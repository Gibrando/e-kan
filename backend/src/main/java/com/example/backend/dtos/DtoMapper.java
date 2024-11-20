package com.example.backend.dtos;

import com.example.backend.dtos.alamatDtos.AlamatPembeliDto;
import com.example.backend.dtos.cartItemDtos.CartItemDto;
import com.example.backend.dtos.itemDtos.ItemDto;
import com.example.backend.dtos.mediaSosialDtos.MediaSosialDto;
import com.example.backend.dtos.pembeliDtos.PembeliDto;
import com.example.backend.dtos.penjualDtos.PenjualDto;
import com.example.backend.models.*;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
    public ItemDto toItemDto(ItemModel model) {
        if (model == null) {
            return null;
        }
        ItemDto dto = ItemDto.builder()
                .id_item(model.getId_item())
                .nama(model.getNama())
                .jenis_habitat(model.getJenis_habitat())
                .jenis_bibit(model.getJenis_bibit())
                .harga(model.getHarga())
                .stock(model.getStock())
                .description(model.getDescription())
                .penjual(toPenjualDto(model.getPenjual())).build();



        return dto;
    }

    public PembeliDto toPembeliDto (PembeliModel model) {
            if (model == null) {
                return null;
            }
            PembeliDto dto = PembeliDto.builder()
                    .id_pembeli(model.getId_pembeli())
                    .nama(model.getNama())
                    .email(model.getEmail())
                    .tanggal_lahir(model.getTanggal_lahir())
                    .no_telp(model.getNo_telp())
                    .createdAt(model.getCreatedAt())
                    .updatedAt(model.getUpdatedAt()).build();
            return dto;
    }
    public AlamatPembeliDto toAlamatDto (AlamatPembeliModel model) {
            if (model == null) {
                return null;
            }
            AlamatPembeliDto dto = AlamatPembeliDto.builder()
                    .id_alamat(model.getId_alamat())
                    .alamat_lengkap(model.getAlamat_lengkap())
                    .kode_pos(model.getKode_pos())
                    .kota(model.getKota())
                    .provinsi(model.getProvinsi())
                    .kabupaten(model.getKabupaten())
                    .keterangan(model.getKeterangan())
                    .pembeli(toPembeliDto(model.getPembeli()))
                    .build();
            return dto;
    }

    public PenjualDto toPenjualDto(PenjualModel model) {
        if (model == null) {
            return null;
        }
        PenjualDto dto = PenjualDto.builder()
                .id_penjual(model.getId_penjual())
                .nama(model.getNama())
                .email(model.getEmail())
                .website(model.getWebsite())
                .alamat(model.getAlamat())
                .no_telp(model.getNo_telp())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .build();
        return dto;
    }

    public MediaSosialDto toMediaSosialDto (MediaSosialModel model) {
        if (model == null) {
            return null;
        }
        MediaSosialDto dto = MediaSosialDto.builder()
                .id_media_sosial(model.getId_media_sosial())
                .platform(model.getPlatform())
                .url(model.getUrl())
                .nama_akun(model.getNama_akun())
                .penjual(toPenjualDto(model.getPenjual())).build();

        return dto ;
    }

    public CartItemDto toCartItemDto(CartItemModel model) {
        if (model == null) {
            return null;
        }
        CartItemDto dto = CartItemDto.builder()
                .id_cart(model.getId_cart())
                .is_checked(model.getIs_checked())
                .jumlah_item(model.getJumlah_item())
                .pembeli(toPembeliDto(model.getPembeli()))
                .item(toItemDto(model.getItem())).build();

        return dto ;
    }
}