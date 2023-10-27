package com.register.login.infrastructure.adapter;

import com.register.login.domain.service.UploadCustomerProfilePicture;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadCustomerProfilePictureAdapter implements UploadCustomerProfilePicture {


  @Override
  public void uploadCustomerProfilePicture(Long customerId, MultipartFile imageId) {

  }
}
