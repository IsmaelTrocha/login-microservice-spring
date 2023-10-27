package com.register.login.domain.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadCustomerProfilePicture {

  void uploadCustomerProfilePicture(Long customerId, MultipartFile file);

}
