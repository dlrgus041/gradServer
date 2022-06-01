package org.grad.project.dto;

import java.util.List;

public class FriendResponse {
    public List<Friend> elements;
    public int total_count, favorite_count;
    public String nickName, profileImageURL, thumbnailURL, before_url, after_url;
}
