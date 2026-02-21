package com.mylinks.auth.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.auth.dto.RegisterRequest;
import com.mylinks.auth.service.PageService;
import com.mylinks.auth.service.SuperAdminService;
import com.mylinks.repo.OrderRepository;
import com.mylinks.repo.PageRepository;
import com.mylinks.repo.RoleRepository;
import com.mylinks.repo.StoreRepository;
import com.mylinks.repo.ThemeRepository;
import com.mylinks.repo.UserRepository;
import com.mylinks.users.entity.Order;
import com.mylinks.users.entity.Page;
import com.mylinks.users.entity.Role;
import com.mylinks.users.entity.Store;
import com.mylinks.users.entity.Theme;
import com.mylinks.users.entity.User;

@RestController
@RequestMapping("/api/superadmin")
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private ThemeRepository themeRepository;
    
    @Autowired
    private StoreRepository storeRepository;
    
    @Autowired
    private PageRepository pageRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired PageService pageService;

    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @PostMapping("/admins")
    public String createAdmin(@RequestBody RegisterRequest request) {
        superAdminService.createAdmin(request);
        return "Admin created successfully";
    }
    
    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @PutMapping("/users/{id}/status")
    public String updateUserStatus(@PathVariable UUID id,
                                   @RequestParam String status) {

        userRepository.updateStatus(id, status);
        return "User status updated";
    }
    
    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @PostMapping("/roles")
    public Role createRole(@RequestParam String name) {
        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }
    
    
    
    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @PostMapping("/roles/{roleId}/permissions/{permissionId}")
    public void assignPermission(@PathVariable UUID roleId,
                                 @PathVariable UUID permissionId) {
        roleRepository.assignPermission(roleId, permissionId);
    }
    
    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @DeleteMapping("/roles/{roleId}/permissions/{permissionId}")
    public void removePermission(@PathVariable UUID roleId,
                                 @PathVariable UUID permissionId) {
        roleRepository.removePermission(roleId, permissionId);
    }
    
    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @PostMapping("/themes")
    public Theme createTheme(@RequestBody Theme theme) {
        return themeRepository.save(theme);
    }
    
    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @PutMapping("/themes/{id}/status")
    public void updateTheme(@PathVariable UUID id,
                            @RequestParam boolean paid) {
        themeRepository.updatePaidStatus(id, paid);
    }
    
    @PreAuthorize("hasAuthority('CREATE_PAGE')")
    @PostMapping("/pages")
    public Page createPage(@RequestParam String title,
                           Authentication auth) {

        return pageService.createPage(auth.getName(), title);
    }
    
    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @GetMapping("/pages")
    public List<Page> getAllPages() {
        return pageRepository.findAll();
    }
    
    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @DeleteMapping("/pages/{id}")
    public void deletePage(@PathVariable UUID id) {
        pageRepository.deleteById(id);
    }
    
    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @GetMapping("/stores")
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }
    
    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @PutMapping("/stores/{id}/status")
    public void updateStoreStatus(@PathVariable UUID id,
                                  @RequestParam String status) {
        storeRepository.updateStatus(id, status);
    }
    
    @PreAuthorize("hasAuthority('FULL_ACCESS')")
    @GetMapping("/orders")
    public List<Order> allOrders() {
        return orderRepository.findAll();
    }
    
}
