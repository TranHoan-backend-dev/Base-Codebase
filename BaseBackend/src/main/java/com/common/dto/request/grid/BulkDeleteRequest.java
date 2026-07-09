package com.common.dto.request.grid;

import lombok.Data;
import java.util.List;

/**
 * Request payload dùng cho thao tác xóa hàng loạt (Bulk Delete).<br/>
 * Created at 21/06/2026
 * 
 * @author txhoan
 */
@Data
public class BulkDeleteRequest {
    private List<String> ids;
}
