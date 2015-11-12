package services;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import model.Usuario;

/**
 *
 * @author Gustavo
 */
public class UsuarioService extends Service<Usuario> {

    @Override
    public void insert(Usuario obj) throws ServiceException {
        if (!findBy("usuario", obj.getUsuario().toUpperCase()).isEmpty()) {
            throw new ServiceException("Usuário deve ser único", null);
        }
        super.insert(obj); //To change body of generated methods, choose Tools | Templates.
    }

    public UsuarioService() {
        super(Usuario.class);
    }

    public boolean autentica(String usuario, String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        List<Usuario> u = findBy("usuario", usuario);
        if (u.isEmpty() || !u.get(0).isAtivo()) {
            return false;
        }

        String senhahex = utils.Utils.criptografa(senha);
        return senhahex.equals(u.get(0).getSenha());
    }
}
