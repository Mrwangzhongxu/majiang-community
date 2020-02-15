package com.whzy.community.dto;

import lombok.Data;

/**
 * Created by codedrinker on 2019/4/24.
 */
@Data
public class GithubUser {
    /**用户名称**/
    private String login;
    /*用户标识id*/
    private Long id;
    /*node_id*/
    private String nodeId;

    private String avatarUrl;
}
