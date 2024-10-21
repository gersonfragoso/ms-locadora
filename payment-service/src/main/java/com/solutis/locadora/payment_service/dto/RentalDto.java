package com.solutis.locadora.payment_service.dto;

import java.util.Date;

public class RentalDto {
    private Long vehicleId;
    private Date dataPedido;
    private Date dataDevolucao;
    private double valor;

    public RentalDto(Long vehicleId, Date dataPedido, Date dataDevolucao, double valor) {
        this.vehicleId = vehicleId;
        this.dataPedido = dataPedido;
        this.dataDevolucao = dataDevolucao;
        this.valor = valor;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}