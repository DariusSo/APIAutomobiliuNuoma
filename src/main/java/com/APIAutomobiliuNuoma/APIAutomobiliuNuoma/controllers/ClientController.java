package com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.controllers;

import com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.Client;
import services.ClientService;
import com.APIAutomobiliuNuoma.APIAutomobiliuNuoma.Rental;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
@RestController
@RequestMapping("/clients")
public class ClientController {
    ClientService clientService = new ClientService();

    @GetMapping
    public List<Client> getClients() throws SQLException {
        return clientService.getClients();
    }
    @PostMapping
    public String addClient(@RequestBody Client c) throws SQLException {
        return clientService.addClient(c);
    }
    @GetMapping("/{id}")
    public Client getClient(@PathVariable int id) throws SQLException {
        return clientService.getClient(id);
    }
    @PutMapping("/{id}")
    public void renewClientInfo(@RequestBody Client c, @PathVariable int id) throws SQLException {
        clientService.renewClientInfo(c, id);
    }
    @DeleteMapping("/{id}")
    public String removeClient(@PathVariable int id) throws SQLException {
        return clientService.removeClient(id);
    }
    @GetMapping("/{id}/rentals")
    public List<Rental> getClientsRentals(@PathVariable int id) throws SQLException {
        return clientService.getClientsRentals(id);
    }
    @GetMapping("/active-rentals")
    public List<Rental> getClientsActiveRentals() throws SQLException {
        return clientService.getClientsActiveRentals();
    }



}
