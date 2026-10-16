
package service;

import model.Cliente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona los clientes precargados del sistema.
 */
public class ClienteService {

    private final List<Cliente> clientes;

    public ClienteService() {
        clientes = new ArrayList<>();

        cargarClientesIniciales();
    }

    private void cargarClientesIniciales() {
        clientes.add(new Cliente(
                "1094900001",
                "Carlos Ramírez",
                "carlos@gmail.com"
        ));

        clientes.add(new Cliente(
                "1094900002",
                "Laura Gómez",
                "laura@gmail.com"
        ));

        clientes.add(new Cliente(
                "1094900003",
                "Andrés Martínez",
                "andres@gmail.com"
        ));

        clientes.add(new Cliente(
                "1094900004",
                "Mariana López",
                "mariana@gmail.com"
        ));
    }

    public List<Cliente> obtenerClientes() {
        return Collections.unmodifiableList(clientes);
    }

    public Cliente buscarPorDocumento(String documento) {
        if (documento == null || documento.isBlank()) {
            return null;
        }

        for (Cliente cliente : clientes) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }

        return null;
    }


    public void registrarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException(
                    "El cliente es obligatorio."
            );
        }

        if (buscarPorDocumento(cliente.getDocumento()) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un cliente con ese documento."
            );
        }

        clientes.add(cliente);
    }

}
