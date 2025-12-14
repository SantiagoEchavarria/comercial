package com.desarrolloweb.comercial.model.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.desarrolloweb.comercial.model.dao.UsuarioDAOIface;
import com.desarrolloweb.comercial.model.entity.Rol;
import com.desarrolloweb.comercial.model.entity.Usuario;

@Service
public class UsuarioDetailService implements UserDetailsService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final UsuarioDAOIface usuarioDAO;

	public UsuarioDetailService(UsuarioDAOIface usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// obtener el usuario de la base de datos
		Usuario usuario = usuarioDAO.findByNombre(username);
		if (usuario == null) {
			logger.info("*** Error de autenticación, el usuario ...");
			throw new UsernameNotFoundException("*** Error de autenticación, el usuario ...");
		}

		// si el usuario existe, obtener sus roles
		List<GrantedAuthority> roles = new ArrayList<>();
		for (Rol rol : usuario.getRoles()) {
			logger.info("*** Rol: " + rol.getNombre());
			roles.add(new SimpleGrantedAuthority(rol.getNombre()));
		}

		// si no hay roles...
		if (roles.isEmpty()) {
			logger.warn("El usuario " + usuario.getNombre() + " no tiene roles asignados");
			throw new UsernameNotFoundException("El usuario " + usuario.getNombre() + " no tiene roles asignados");
		}
        
		// retornar un usuario del tipo Security
		return new User(usuario.getNombre(), usuario.getClave(), usuario.isActivo(), true, true, true, roles);
	}

}
