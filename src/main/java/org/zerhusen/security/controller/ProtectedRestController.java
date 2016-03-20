package org.zerhusen.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${jwt.route.protected.path}")
public class ProtectedRestController {

    /**
     * This is an example of some different kinds of granular restriction for endpoints. You can use the built-in SPEL expressions
     * in @PreAuthorize such as 'hasRole()' to determine if a user has access. However, if you require logic beyond the methods
     * Spring provides then you can encapsulate it in a service and register it as a bean to use it within the annotation as
     * demonstrated below with 'securityService'.
     **/
    @RequestMapping(method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("@securityService.hasProtectedAccess()")
    public ResponseEntity<?> getDaHoney() {
        return ResponseEntity.ok(":O");
    }

}