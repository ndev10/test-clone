package com.oauth.service;


import com.oauth.dto.UserDetailsDTO;
import com.oauth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;

	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		UserDetailsDTO userDetailsDto = userRepository.findByUsernameAndOrganizationCode(userName);
		if(userDetailsDto == null) {
			throw new UsernameNotFoundException(String.format("User with the username %s doesn't exist", userName));
		}

		return userDetailsDto;
	}
}
