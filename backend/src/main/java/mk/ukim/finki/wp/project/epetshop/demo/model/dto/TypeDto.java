package mk.ukim.finki.wp.project.epetshop.demo.model.dto;

import lombok.Data;

@Data
public class TypeDto {
    private String name;

    public String getName() {
        return name;
    }

    public TypeDto() {
    }

    public TypeDto(String name) {
        this.name = name;
    }
}
