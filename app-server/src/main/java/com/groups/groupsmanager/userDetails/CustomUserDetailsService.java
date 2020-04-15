package com.groups.groupsmanager.userDetails;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.groups.groupsmanager.model.User;
import com.groups.groupsmanager.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
		return CustomUserDetailsPrincipal.create(user);
	}

	@Transactional
	public UserDetails loadUserId(Long id) throws UsernameNotFoundException {
		User user = userRepo.findById(new Long(id))
				.orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

		return CustomUserDetailsPrincipal.create(user);
	}

}
