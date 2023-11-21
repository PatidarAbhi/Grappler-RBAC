    package com.grapplermodule1.GrapplerEnhancement.cerebrus.config;

    import com.grapplermodule1.GrapplerEnhancement.entities.Role;
    import com.grapplermodule1.GrapplerEnhancement.entities.Users;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;

    import java.util.Collection;
    import java.util.List;

    public class UserDetailsConfig implements UserDetails {
        private Users user;

        public UserDetailsConfig(Users users) {
            super();
            this.user = users;
        }

        public Users getUser() {
            return user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole().getRole());
            return List.of(simpleGrantedAuthority);
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }
        @Override
        public String getUsername() {
            return user.getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

    }
