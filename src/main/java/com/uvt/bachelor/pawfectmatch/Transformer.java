package com.uvt.bachelor.pawfectmatch;

import com.uvt.bachelor.pawfectmatch.entity.*;
import com.uvt.bachelor.pawfectmatch.model.*;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

public class Transformer {

    public static PetDto toDto(Pet entity) {
        return toDto(entity, null);
    }

    public static PetDto toDto(Pet entity, Double distanceToSelectedPet) {
        var dto = new PetDto();
        if (!ObjectUtils.isEmpty(entity)) {
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setBirthdate(entity.getBirthdate());
            dto.setAge(AgeCalculator.calculateAge(entity.getBirthdate()));
            dto.setType(entity.getType());
            dto.setDescription(entity.getDescription());
            dto.setBreed(entity.getBreed());
            dto.setGender(entity.getGender());
            dto.setColor(entity.getColor());
            dto.setOwner(toDto(entity.getOwner()));
            if (!isEmpty(entity.getPhotos())) {
                dto.setPhotoIds(mapPhotosToDto(entity.getPhotos()));
            }
            if (!isEmpty(distanceToSelectedPet)) {
                dto.setDistance(distanceToSelectedPet.intValue());
            }
        }
        return dto;
    }

    public static ChatMessageDto toDto(ChatMessage entity, Pet sender, Pet receiver) {
        var dto = new ChatMessageDto();
        if (!ObjectUtils.isEmpty(entity)) {
            dto.setId(entity.getId());
            dto.setContent(entity.getContent());
            dto.setSenderId(entity.getSenderId());
            dto.setReceiverId(entity.getReceiverId());
            dto.setSenderName(sender.getName());
            dto.setReceiverName(receiver.getName());
            String senderOwner = sender.getOwner().getFirstName() + " " + sender.getOwner().getLastName();
            String receiverOwner = receiver.getOwner().getFirstName() + " " + receiver.getOwner().getLastName();
            dto.setSenderOwner(senderOwner);
            dto.setReceiverOwner(receiverOwner);
        }
        return dto;
    }

    public static UserDto toDto(User entity) {
        var dto = new UserDto();
        if (!ObjectUtils.isEmpty(entity)) {
            dto.setId(entity.getId());
            dto.setEnabled(entity.getEnabled());
            dto.setUsername(entity.getUsername());
            dto.setPassword(entity.getPassword());
        }
        return dto;
    }

    public static List<Long> mapPhotosToDto(Set<PetImage> photos) {
        return photos.stream().map(PetImage::getId).collect(Collectors.toList());
    }

    public static Pet fromDto(PetDto dto) {
        var entity = new Pet();
        entity.setName(dto.getName());
        entity.setBirthdate(dto.getBirthdate());
        entity.setType(dto.getType());
        entity.setDescription(dto.getDescription());
        entity.setBreed(dto.getBreed());
        entity.setGender(dto.getGender());
        entity.setColor(dto.getColor());
        return entity;
    }

    public static PetOwnerDto toDto(PetOwner entity) {
        var dto = new PetOwnerDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setAddress(getAddress(entity));
        return dto;
    }

    private static String getAddress(PetOwner owner) {
        return owner.getAddress().getCity() + ", " + owner.getAddress().getCountry();
    }
}
