package org.tg.ui;

import org.tg.dao.auth.AuthUserDao;
import org.tg.domains.auth.UserEntity;
import org.tg.domains.auth.UserOTPEntity;
import org.tg.dto.OtpDto;
import org.tg.dto.auth.Session;
import org.tg.dto.auth.UserCreateDTO;
import org.tg.dto.response.ResponseEntity;
import org.tg.enums.Language;
import org.tg.exceptions.CustomSQLException;
import org.tg.services.auth.AuthService;
import uz.jl.BaseUtils;
import uz.jl.Colors;

import java.time.LocalDateTime;
import java.util.Objects;


public class AuthUI {
    
    AuthService service = new AuthService();
    AuthUserDao authUserDao = new AuthUserDao();

    public static void main(String[] args) {
        AuthUI authUI = new AuthUI();
        if (Objects.isNull(Session.sessionUser)) {
            BaseUtils.println("Login -> 1");
            BaseUtils.println("Register -> 2");
        } else {
            BaseUtils.println(" -> 2");
            BaseUtils.println("Chats       -> 3");
            BaseUtils.println("Settings    -> 4");
            BaseUtils.println("Logout      -> 5");
        }
        BaseUtils.println("Quit -> q");
        String choice = BaseUtils.readText("?:");
        switch (choice) {
            case "1" -> authUI.login();
            case "2" -> authUI.register();
            case "3" -> authUI.chats();
            case "4" -> authUI.settings();
            case "5" -> authUI.logout();
            case "q" -> {
                BaseUtils.println("Bye");
                System.exit(0);
            }
            default -> BaseUtils.println("Wrong Choice", Colors.RED);
        }
        main(args);
    }

    private void chats() {

    }

    private void settings() {

    }

    private void logout() {


    }


    private void login() {
        String username = BaseUtils.readText("username ");
        String password = BaseUtils.readText("password ");
        print_response(service.login(username, password));
    }

    private void register() {
        try {
            String phoneNumber = BaseUtils.readText("Phone Number : ");
            UserEntity userEntity = authUserDao.findUserByPhoneNumber(phoneNumber);
            if (Objects.nonNull(userEntity)) {
                BaseUtils.println("Phone number already registered", Colors.RED);
                return;
            }
            String otp = BaseUtils.otp(6);
            OtpDto otpDto = OtpDto.builder()
                    .otp(otp)
                    .phone(phoneNumber)
                    .expires(LocalDateTime.now().plusMinutes(2))
                    .build();
            service.sendOtp(otpDto);

            String receiverOtp = BaseUtils.readText("otp:");
            UserOTPEntity userOTPEntity = service.findByOtpAndPhoneNumber(receiverOtp, phoneNumber);
            if (Objects.isNull(userOTPEntity)) {
                BaseUtils.println("Invalid OTP", Colors.RED);
                return;
            }
            if (userOTPEntity.getExpires().isBefore(LocalDateTime.now())) {
                BaseUtils.println("Invalid OTP", Colors.RED);
                return;
            }

            String firstName = BaseUtils.readText("First Name : ");
            String lastName = BaseUtils.readText("Last Name : ");

            UserCreateDTO userCreateDTO = UserCreateDTO.builder()
                    .language(Language.RU.name())
                    .firstName(firstName)
                    .lastName(lastName)
                    .phone(phoneNumber)
                    .build();

            print_response(service.register(userCreateDTO));

        } catch (CustomSQLException e) {
            BaseUtils.println(e.getLocalizedMessage(), Colors.RED);
        }

    }

    public void print_response(ResponseEntity response) {
        String color = response.getStatus() != 200 ? Colors.RED : Colors.GREEN;
        BaseUtils.println(BaseUtils.gson.toJson(response.getData()), color);
    }

}
