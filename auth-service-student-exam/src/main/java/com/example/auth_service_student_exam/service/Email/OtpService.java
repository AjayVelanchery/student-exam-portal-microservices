package com.example.auth_service_student_exam.service.Email;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final StringRedisTemplate redisTemplate;

    public String generateOtp(String email) {
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        redisTemplate.opsForValue().set("OTP:" + email, otp, 5, TimeUnit.MINUTES);
        return otp;
    }

    public boolean verifyOtp(String email, String otp) {
        String stored = redisTemplate.opsForValue().get("OTP:" + email);
        return stored != null && stored.equals(otp);
    }

    public void deleteOtp(String email) {
        redisTemplate.delete("OTP:" + email);
    }
}
