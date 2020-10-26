package br.com.saraware.dao;

import java.util.ArrayList;

public interface IDAO  {
	public void cadastrar (Object param) throws Exception;
	public void alterar (Object param) throws Exception;
	public void deletar (Object param) throws Exception;
	public ArrayList listar () throws Exception;
	public ArrayList buscar (String valor) throws Exception;
	}
