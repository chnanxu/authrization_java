package com.chen.pojo.page;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Left_NavbarItem {
    private long item_id;
    private String item_name;
    private String item_ico;
    private String item_href;

}
