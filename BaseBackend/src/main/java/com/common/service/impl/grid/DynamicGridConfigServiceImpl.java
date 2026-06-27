package com.common.service.impl.grid;

import com.common.dto.response.grid.GridActionConfig;
import com.common.dto.response.grid.GridColumnConfig;
import com.common.dto.response.grid.GridConfigResponse;
import com.common.service.contract.grid.DynamicGridConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

import com.common.exception.ResourceNotFoundException;
import com.common.model.sql.grid.DynamicGridConfig;
import com.common.repository.sql.DynamicGridConfigRepository;
import com.common.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.Collections;

/**
 * Implementation cho DynamicGridConfigService.
 * 
 * Created at 21/06/2026
 * @author txhoan
 */
@Service
@RequiredArgsConstructor
public class DynamicGridConfigServiceImpl implements DynamicGridConfigService {

    private final DynamicGridConfigRepository dynamicGridConfigRepository;
    private final MessageService messageService;

    @Override
    @Transactional("systemTransactionManager")
    public GridConfigResponse getConfig(String gridCode) {
        DynamicGridConfig configEntity = dynamicGridConfigRepository.findByGridKey(gridCode)
                .orElseThrow(() -> new ResourceNotFoundException(messageService.get("error.grid.not.found", gridCode)));

        return GridConfigResponse.builder()
                .gridCode(configEntity.getGridKey())
                .title(configEntity.getGridLabel())
                .layoutOptions(GridConfigResponse.LayoutOptions.builder()
                        .rowSelection(true) // Có thể lấy từ configEntity nếu cần trong tương lai
                        .pagination(true)
                        .defaultPageSize(20)
                        .stickyHeader(true)
                        .build())
                .columns(configEntity.getColumns().stream()
                        .filter(c -> c.isVisible())
                        .map(c -> GridColumnConfig.builder()
                                .field(c.getFieldName())
                                .header(c.getHeaderLabel())
                                .type(c.getDataType() != null ? c.getDataType().name() : "TEXT")
                                .sortable(c.isSortable())
                                .build())
                        .collect(Collectors.toList()))
                .filters(Collections.emptyList()) // Sẽ cập nhật khi có Entity tương ứng
                .actions(Collections.emptyList()) // Sẽ cập nhật khi có Entity tương ứng
                .build();
    }
}
