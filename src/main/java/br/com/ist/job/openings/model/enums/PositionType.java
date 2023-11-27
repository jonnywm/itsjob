package br.com.ist.job.openings.model.enums;

/**
 *
 * @author jonny
 */
public enum PositionType {

    CLT("positionType.clt"), PJ("positionType.pj"), FREE_LANCER("positionType.freeLancer");

    private final String description;

    private PositionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
