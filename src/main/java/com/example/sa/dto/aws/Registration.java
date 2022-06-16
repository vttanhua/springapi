package com.example.sa.dto.aws;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.example.sa.utils.ValidInvitationCode;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Registration {

	@NotBlank
	private String username;
	@Email
	private String email;
	@ValidInvitationCode
	private String invitationCode;
}
