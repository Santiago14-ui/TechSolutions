
package controller;

import model.Cliente;
import service.ClienteService;

import java.util.List;

public class ClientesController {

    private final ClienteService clienteService;

    public ClientesController(ClienteService clienteService) {
        if (clienteService == null) {
            throw new IllegalArgumentException(
                    "El servicio de clientes es obligatorio."
            );
        }

        this.clienteService = clienteService;
    }

    public List<Cliente> obtenerClientes() {
        return clienteService.obtenerClientes();
    }

    public Cliente buscarPorDocumento(String documento) {
        return clienteService.buscarPorDocumento(documento);
    }

    public void registrarCliente(Cliente cliente) {clienteService.registrarCliente(cliente);}

}
