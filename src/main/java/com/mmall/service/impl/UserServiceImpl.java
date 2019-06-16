package com.mmall.service.impl;

import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;

import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        //System.out.println("111");
        int resultCount = 0;
        System.out.println(username);
        resultCount = userMapper.checkUsername(username);

        if(resultCount==0){
            return ServerResponse.createByErrorMessage("This user does not exist.");
        }
        //System.out.println("222");
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username,md5Password);

        if(user == null){
            return ServerResponse.createByErrorMessage("Password wrong!");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("Login success.", user);
    }

    public ServerResponse<String> register(User user){


        ServerResponse validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);

        //MD5
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if(resultCount==0){
            return ServerResponse.createByErrorMessage("Registration fail.");
        }
        return ServerResponse.createBySuccessMessage("Registration success");



    }

    public ServerResponse<String> checkValid(String str, String type){
        if(StringUtils.isNotBlank(type)){
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount>0){
                    return ServerResponse.createByErrorMessage("Username already exists.");
                }
            }
            if(Const.EMAIL.equals((type))){
                int resultCount = userMapper.checkEmail(str);
                if(resultCount>0){
                    return ServerResponse.createByErrorMessage("Email already exists.");
                }
            }


        }else{
            return ServerResponse.createByErrorMessage("Parameter error.");
        }

        return ServerResponse.createBySuccessMessage("Validation check success.");
    }

    public ServerResponse selectQuestion(String username){
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            //user not exists
            return ServerResponse.createByErrorMessage("User not exists.");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if(StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByErrorMessage("The question of finding password is blank.");
    }

    public ServerResponse<String> checkAnswer(String username, String question, String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount>0){
            //check success
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("Wrong answer for the question.");
    }

    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken){
        if(StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("Parameter wrong, token is needed");
        }
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            //user not exists
            return ServerResponse.createByErrorMessage("User not exists.");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
        if(StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("Token invalid or expired.");
        }
        if(StringUtils.equals(forgetToken,token)){
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username,md5Password);
            if(rowCount>0){
                return ServerResponse.createBySuccessMessage("Update password success.");
            }
        }
        else{
            ServerResponse.createByErrorMessage("Token is wrong, please get the new token to reset password");
        }
        return ServerResponse.createByErrorMessage("Update password failed.");

    }

    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user){
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("Old password is wrong.");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount>0){
            return ServerResponse.createBySuccessMessage("Password reset success.");
        }
        return ServerResponse.createByErrorMessage("Update password failed.");
    }

    public ServerResponse<User> updateInformation(User user){
        //username cannot be updated
        //check email, it cannot be the email hold by any others
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount>0){
            return ServerResponse.createByErrorMessage("This email exists already, please input another one.");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setUsername(user.getUsername());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount>0){
            return ServerResponse.createBySuccess("Update information success",updateUser);
        }
        return ServerResponse.createByErrorMessage("Update information failed.");

    }

    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user==null){
            return ServerResponse.createByErrorMessage("Current user cannot be finded.");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    public ServerResponse checkAdminRole(User user){
        if(user!=null && user.getRole()==Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        else return ServerResponse.createByError();
    }

}
