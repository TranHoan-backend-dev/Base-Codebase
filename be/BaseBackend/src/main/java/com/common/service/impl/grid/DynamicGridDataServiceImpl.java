package com.common.service.impl.grid;

import com.common.dto.request.grid.GridDataRequest;
import com.common.service.contract.grid.DynamicGridDataService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import org.springframework.jdbc.core.JdbcTemplate;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Implementation cho DynamicGridDataService.
 * 
 * Created at 21/06/2026
 * @author txhoan
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DynamicGridDataServiceImpl implements DynamicGridDataService {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Object getData(String gridCode, GridDataRequest request) {
        log.info("Fetching data for grid {} with request {}", gridCode, request);
        
        // Sử dụng JdbcTemplate để query động từ database (giả định gridCode là tên bảng/view)
        // Lưu ý: Cần sanitize gridCode để tránh SQL Injection nếu nó từ user input.
        // Trong hệ thống thực tế, gridCode nên được map với một cấu hình bảng an toàn.
        var tableName = gridCode.toLowerCase();
        
        var limit = request.getSize() > 0 ? request.getSize() : 20;
        var offset = request.getPage() * limit;

        var dataQuery = String.format("SELECT * FROM %s LIMIT ? OFFSET ?", tableName);
        List<Map<String, Object>> data = jdbcTemplate.queryForList(dataQuery, limit, offset);

        var countQuery = String.format("SELECT COUNT(*) FROM %s", tableName);
        var total = jdbcTemplate.queryForObject(countQuery, Integer.class);

        return Map.of(
            "data", data,
            "total", total != null ? total : 0,
            "page", request.getPage(),
            "size", limit
        );
    }

    @Override
    public void bulkDelete(String gridCode, List<String> ids) {
        log.info("Bulk delete on grid {} for ids {}", gridCode, ids);
        if (ids == null || ids.isEmpty()) return;
        
        var tableName = gridCode.toLowerCase();
        // Xây dựng chuỗi tham số "?, ?, ?"
        var placeholders = String.join(",", ids.stream().map(id -> "?").toList());
        var deleteQuery = String.format("DELETE FROM %s WHERE id IN (%s)", tableName, placeholders);
        
        jdbcTemplate.update(deleteQuery, ids.toArray());
    }
}
