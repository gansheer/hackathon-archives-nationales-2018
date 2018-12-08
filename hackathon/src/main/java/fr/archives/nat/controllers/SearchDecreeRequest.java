package fr.archives.nat.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Patrick Allain - 12/9/18.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDecreeRequest {

    private String firstName;

    private String lastName;

    private String username;
}
