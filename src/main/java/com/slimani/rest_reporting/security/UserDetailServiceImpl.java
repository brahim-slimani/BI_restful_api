package com.slimani.rest_reporting.security;


import com.slimani.rest_reporting.dao.UserRepository;
import com.slimani.rest_reporting.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
	private final UserRepository repository;
	
	@Autowired
	public UserDetailServiceImpl(UserRepository userRepository){
		this.repository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User curruser = repository.findUserByName(username);
		UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPassword(),
        		AuthorityUtils.createAuthorityList(curruser.getRole()));
        return user;
	}
}
