package com.api.note.utils.services;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Service
public class GetFullImageURL {
    public String getURL(String imageURL, String path) {
        String URI = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        return URI + "/" + path + "/" + imageURL;
    }
}
