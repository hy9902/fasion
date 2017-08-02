package com.hydt.app.vo;

import com.hydt.app.config.dataSource.DataSource;
import lombok.*;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;

/**
 * Created by bean_huang on 2017/8/2.
 */
@Data
@Log4j
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private Integer age;

}
