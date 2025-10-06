package br.edu.ufersa.mimic.service.auth;

import br.edu.ufersa.mimic.model.auth.Usuario;
import br.edu.ufersa.mimic.repository.auth.UsuarioRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
        public class UserDetailsServiceImpl implements UserDetailsService {
        private final UsuarioRepository repository;

        public UserDetailsServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario currentUser = repository.findByEmail(email);
        if (currentUser != null) {
            UserDetails user = new org.springframework.security.core.userdetails.User(email, currentUser.getSenha(),
                    true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER"));
            return user;
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
    }
}
