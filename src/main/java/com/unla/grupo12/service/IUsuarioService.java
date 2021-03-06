package com.unla.grupo12.service;

import com.unla.grupo12.entity.Usuario;
import com.unla.grupo12.model.UsuarioModel;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface IUsuarioService {

	public UsuarioModel agregarOActualizar(UsuarioModel usuarioModel);

	public List<Usuario> getAll();

	public UsuarioModel findById(Long id);

	public boolean darDeBaja(Long id);

	public boolean logoutUsuario();

	public List<UsuarioModel> listUsuarios();

	public ByteArrayInputStream generacionPdf();

}
