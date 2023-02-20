package rs.raf.demo.responses;

import lombok.Data;
import rs.raf.demo.model.Permission;

@Data
public class PermissionResponse {
    private Permission permission;

    public PermissionResponse(Permission permission) {
        this.permission = permission;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
