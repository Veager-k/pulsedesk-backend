package com.pulsedesk.pulsedesk_backend.dto;

import lombok.Getter;
import lombok.Setter;

public class TicketAnalysisResult {

    @Setter
    private boolean shouldCreateTicket;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String category;

    @Getter
    @Setter
    private String priority;

    @Getter
    @Setter
    private String summary;

    public boolean isShouldCreateTicket() {
        return shouldCreateTicket;
    }
}
