package com.hydt.app.vo;

import lombok.*;
import lombok.extern.log4j.Log4j;

/**
 * Created by bean_huang on 2017/8/2.
 */
@Data
@Log4j
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private Long id;
    private String name;
    private Integer age;

}
