package com.homihq.manager.core.event;

import com.homihq.manager.core.domain.User;
import io.vavr.collection.HashMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.Locale;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRegisteredEvent {
    private User user;
    private String url;
    private Locale locale;
    private String token;

    public Map<String,Object> toMap(){
        java.util.HashMap data =
        HashMap.of("email", user.getEmail()
                ,"url", url
                ,"fullName", String.join(" " , user.getFirstName(), user.getLastName())
                ,"token", token)
                .toJavaMap();

        return data;

    }
}
