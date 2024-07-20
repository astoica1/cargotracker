package org.eclipse.cargotracker.domain.model.cargo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import org.apache.commons.lang3.Validate;
import org.eclipse.cargotracker.domain.model.handling.HandlingEvent;
import org.eclipse.cargotracker.domain.model.location.Location;
import org.eclipse.cargotracker.domain.model.voyage.Voyage;

/**
 * A handling activity represents how and where a cargo can be handled, and can be used to express
 * predictions about what is expected to happen to a cargo in the future.
 */
@Embeddable
public class HandlingActivity implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  @Enumerated(EnumType.STRING)
  @Column(name = "next_expected_handling_event_type")
  @NotNull(message = "Handling event type is required.")
  private HandlingEvent.Type type;

  @ManyToOne
  @JoinColumn(name = "next_expected_location_id")
  @NotNull(message = "Location is required.")
  private Location location;

  @ManyToOne
  @JoinColumn(name = "next_expected_voyage_id")
  private Voyage voyage;

  public HandlingActivity() {}

  public HandlingActivity(HandlingEvent.Type type, Location location) {
    Validate.notNull(type, "Handling event type is required.");
    Validate.notNull(location, "Location is required.");

    this.type = type;
    this.location = location;
  }

  public HandlingActivity(HandlingEvent.Type type, Location location, Voyage voyage) {
    Validate.notNull(type, "Handling event type is required");
    Validate.notNull(location, "Location is required");
    Validate.notNull(voyage, "Voyage is required");

    this.type = type;
    this.location = location;
    this.voyage = voyage;
  }

  public HandlingEvent.Type getType() {
    return type;
  }

  public Location getLocation() {
    return location;
  }

  public Voyage getVoyage() {
    return voyage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof HandlingActivity that)) return false;

    return type == that.type
        && Objects.equals(location, that.location)
        && Objects.equals(voyage, that.voyage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, location, voyage);
  }

  public boolean isEmpty() {
    if (type != null) {
      return false;
    }

    if (location != null) {
      return false;
    }

    return voyage == null;
  }
}
