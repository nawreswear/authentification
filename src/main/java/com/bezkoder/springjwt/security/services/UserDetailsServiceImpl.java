package com.bezkoder.springjwt.security.services;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with email: " + username);
		}
		return UserDetailsImpl.build(user.getType(),user);
	}
	public User save(User user) {
		return userRepository.save(user);
	}

	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}
	public void update(User updatedUser) {
		if (userRepository != null) {
			User existingUser = userRepository.findById(updatedUser.getId())
					.orElseThrow(() -> new IllegalArgumentException("User not found with id: " + updatedUser.getId()));
			existingUser.setType(updatedUser.getType());
			existingUser.setNom(updatedUser.getNom());
			existingUser.setPrenom(updatedUser.getPrenom());
			existingUser.setTel(updatedUser.getTel());
			existingUser.setEmail(updatedUser.getEmail());
			existingUser.setPassword(updatedUser.getPassword());
			existingUser.setCodePostal(updatedUser.getCodePostal());
			existingUser.setPays(updatedUser.getPays());
			existingUser.setVille(updatedUser.getVille());
			existingUser.setCin(updatedUser.getCin());
			existingUser.setLongitude(updatedUser.getLongitude());
			existingUser.setLatitude(updatedUser.getLatitude());
			userRepository.save(existingUser);
		}
	}

}
