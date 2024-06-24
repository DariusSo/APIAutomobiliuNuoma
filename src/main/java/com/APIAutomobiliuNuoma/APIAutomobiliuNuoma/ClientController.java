package com.APIAutomobiliuNuoma.APIAutomobiliuNuoma;

import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@RestController
public class ClientController {

    @GetMapping("/getClients")
    public List<Client> getClients() throws SQLException {
        List<Client> clients = new ArrayList<>();
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM client");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Client client = new Client(rs.getInt("client_id"), rs.getString("first_name"), rs.getString("last_name"),
                    rs.getString("email"), rs.getString("phone"));
            clients.add(client);
        }
        return clients;
    }
    @PostMapping("/addClient")
    public String addClient(@RequestBody Client c) throws SQLException {
        if(c.firstName == null || c.lastName == null || c.email == null || c.phone == null){
            return "Truksta paramentru";
        }
        PreparedStatement ps = manageSQL.SQLConnection("INSERT INTO client (first_name, last_name, email, phone) VALUES (?,?,?,?)");
        ps.setString(1, c.firstName);
        ps.setString(2, c.lastName);
        ps.setString(3, c.email);
        ps.setString(4, c.phone);
        ps.executeUpdate();
        return "Klientas pridetas";
    }
    @GetMapping("/getClientById")
    public static Client getClient(int id) throws SQLException {
        Client client = null;
        PreparedStatement ps = manageSQL.SQLConnection("SELECT * FROM client WHERE client_id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            client = new Client(rs.getInt("client_id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), rs.getString("phone"));

        }
        if (client == null){
            System.out.println("Toks klientas nerastas");
        }
        return client;
    }
    @PutMapping("/renewClientInfo")
    public void renewCarInfo(@RequestBody Client c) throws SQLException {
        PreparedStatement ps = manageSQL.SQLConnection("UPDATE client " +
                "SET first_name = ?," +
                "last_name = ?," +
                "email = ?," +
                "phone = ?" +
                " WHERE client_id = ?");
        ps.setString(1, c.firstName);
        ps.setString(2, c.lastName);
        ps.setString(3, c.email);
        ps.setString(4, c.phone);
        ps.setInt(5, c.id);
        ps.executeUpdate();
    }
    @DeleteMapping("/removeClient")
    public String removeClient(int id) throws SQLException {
        List<Client> clientList = getClients();
        for(Client c : clientList){
            if (c.id == id){
                PreparedStatement ps = manageSQL.SQLConnection("DELETE FROM client WHERE client_id = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                return "Klientas sekmingai pasalintas";
            }
        }
        return "Tokio kliento nera";
    }

}
