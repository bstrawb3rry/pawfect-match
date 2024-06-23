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
        var dto = new PetDto();
        if (!ObjectUtils.isEmpty(entity)) {
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setAge(entity.getAge());
            dto.setType(entity.getType());
            dto.setDescription(entity.getDescription());
            dto.setBreed(entity.getBreed());
            dto.setGender(entity.getGender());
            dto.setColor(entity.getColor());
            dto.setOwner(toDto(entity.getOwner()));
            if (!isEmpty(entity.getPhotos())) {
                dto.setPhotoIds(mapPhotosToDto(entity.getPhotos()));
            }
        }
        return dto;
    }

    public static ChatMessageDto toDto(ChatMessage entity, PetOwner sender, PetOwner receiver) {
        var dto = new ChatMessageDto();
        if (!ObjectUtils.isEmpty(entity)) {
            dto.setId(entity.getId());
            dto.setContent(entity.getContent());
            dto.setSenderId(entity.getSenderId());
            dto.setReceiverId(entity.getReceiverId());
            String senderName = sender.getFirstName() + " " + sender.getLastName();
            String receiverName = receiver.getFirstName() + " " + receiver.getLastName();
            dto.setSenderName(senderName);
            dto.setReceiverName(receiverName);
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
        entity.setAge(dto.getAge());
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
        return dto;
    }

    public static PetOwner fromDto(PetOwnerDto dto) {
        var entity = new PetOwner();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        return entity;
    }

    public static MatchDto toDto(Match match) {
        var dto = new MatchDto();
        dto.setFullMatch(match.getFullMatch());
        dto.setId(match.getId());
        dto.setInitiator(toDto(match.getPetInitMatch()));
        dto.setReceiver(toDto(match.getPetResponseMatch()));
        dto.setMatchDate(match.getMatchDate());
        return dto;
    }
}
