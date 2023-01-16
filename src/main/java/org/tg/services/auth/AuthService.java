package org.tg.services.auth;

import org.tg.dao.auth.AuthUserDao;
import org.tg.dao.auth.UserOTPDao;
import org.tg.domains.auth.UserEntity;
import org.tg.domains.auth.UserOTPEntity;
import org.tg.dto.OtpDto;
import org.tg.dto.auth.Session;
import org.tg.dto.auth.UserCreateDTO;
import org.tg.dto.auth.UserDTO;
import org.tg.dto.response.AppErrorDTO;
import org.tg.dto.response.DataDTO;
import org.tg.dto.response.ResponseEntity;
import org.tg.exceptions.CustomSQLException;
import org.tg.services.MailService;
import uz.jl.BaseUtils;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


public class AuthService {

    AuthUserDao authUserDao = new AuthUserDao();
    MailService mailService = new MailService();
    UserOTPDao userOTPDao = new UserOTPDao();

    public ResponseEntity<DataDTO<UserDTO>> login(String username, String password) {
        try {
            Optional<String> jsonDataOptional = authUserDao.login(username, password);
            if (jsonDataOptional.isEmpty()) {
                return new ResponseEntity<>(new DataDTO<>(
                        AppErrorDTO
                                .builder()
                                .friendlyMessage("Something wrong. Plese try later")
                                .developerMessage("user_login() prodsedure returned null check it out(hr.user_login(username, password)")
                                .build()),
                        404);
            }

            UserDTO dto = BaseUtils.gson.fromJson(jsonDataOptional.get(), UserDTO.class);
            Session.setSessionUser(dto);
            return new ResponseEntity<>(new DataDTO<>(dto), 200);

        } catch (CustomSQLException e) {
            return new ResponseEntity<>(new DataDTO<>(
                    AppErrorDTO
                            .builder()
                            .friendlyMessage(e.getMessage())
                            .build()),
                    500);
        }
    }

    public ResponseEntity<DataDTO<Long>> register(UserCreateDTO dto) {
        try {

            Optional<Long> userOptional = authUserDao.register(dto);
            if (userOptional.isEmpty()) {
                return new ResponseEntity<>(new DataDTO<>(
                        AppErrorDTO
                                .builder()
                                .friendlyMessage("Something wrong try again")
                                .developerMessage("Bro check your user_create() function")
                                .build()
                ), 500);
            }

            Long userID = userOptional.get();
            return new ResponseEntity<>(new DataDTO<>(userID), 200);
        } catch (CustomSQLException e) {
            return new ResponseEntity<>(new DataDTO<>(
                    AppErrorDTO
                            .builder()
                            .friendlyMessage(e.getLocalizedMessage())
                            .developerMessage(e.getCause().getMessage())
                            .build()
            ), 500);
        }
    }

    public UserOTPEntity findByOtpAndPhoneNumber(String receiverOtp, String phoneNumber) {
        return null;
    }

    public void sendOtp(final OtpDto otpDto) {
//        CompletableFuture.runAsync(() -> {
            try {
                mailService.sendMessage("Activation Code", otpDto.getOtp(), otpDto.getPhone());
                userOTPDao.save(otpDto);
            } catch (CustomSQLException e) {
                e.printStackTrace();
            }
//        });
    }

    public UserEntity findUserByPhoneNumber(String phoneNumber) throws CustomSQLException {
        return authUserDao.findUserByPhoneNumber(phoneNumber);
    }
}
