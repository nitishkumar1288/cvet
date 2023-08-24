package com.fluentcommerce.graphql.type;

import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.InputFieldMarshaller;
import com.apollographql.apollo.api.InputFieldWriter;
import com.apollographql.apollo.api.internal.Utils;
import java.io.IOException;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Generated("Apollo GraphQL")
public final class UpdateOpeningScheduleInput {
  private final @Nonnull String id;

  private final Input<Boolean> allHours;

  private final Input<Integer> monStart;

  private final Input<Integer> monEnd;

  private final Input<Integer> tueStart;

  private final Input<Integer> tueEnd;

  private final Input<Integer> wedStart;

  private final Input<Integer> wedEnd;

  private final Input<Integer> thuStart;

  private final Input<Integer> thuEnd;

  private final Input<Integer> friStart;

  private final Input<Integer> friEnd;

  private final Input<Integer> satStart;

  private final Input<Integer> satEnd;

  private final Input<Integer> sunStart;

  private final Input<Integer> sunEnd;

  UpdateOpeningScheduleInput(@Nonnull String id, Input<Boolean> allHours, Input<Integer> monStart,
      Input<Integer> monEnd, Input<Integer> tueStart, Input<Integer> tueEnd,
      Input<Integer> wedStart, Input<Integer> wedEnd, Input<Integer> thuStart,
      Input<Integer> thuEnd, Input<Integer> friStart, Input<Integer> friEnd,
      Input<Integer> satStart, Input<Integer> satEnd, Input<Integer> sunStart,
      Input<Integer> sunEnd) {
    this.id = id;
    this.allHours = allHours;
    this.monStart = monStart;
    this.monEnd = monEnd;
    this.tueStart = tueStart;
    this.tueEnd = tueEnd;
    this.wedStart = wedStart;
    this.wedEnd = wedEnd;
    this.thuStart = thuStart;
    this.thuEnd = thuEnd;
    this.friStart = friStart;
    this.friEnd = friEnd;
    this.satStart = satStart;
    this.satEnd = satEnd;
    this.sunStart = sunStart;
    this.sunEnd = sunEnd;
  }

  /**
   *  ID of the object
   */
  public @Nonnull String id() {
    return this.id;
  }

  public @Nullable Boolean allHours() {
    return this.allHours.value;
  }

  public @Nullable Integer monStart() {
    return this.monStart.value;
  }

  public @Nullable Integer monEnd() {
    return this.monEnd.value;
  }

  public @Nullable Integer tueStart() {
    return this.tueStart.value;
  }

  public @Nullable Integer tueEnd() {
    return this.tueEnd.value;
  }

  public @Nullable Integer wedStart() {
    return this.wedStart.value;
  }

  public @Nullable Integer wedEnd() {
    return this.wedEnd.value;
  }

  public @Nullable Integer thuStart() {
    return this.thuStart.value;
  }

  public @Nullable Integer thuEnd() {
    return this.thuEnd.value;
  }

  public @Nullable Integer friStart() {
    return this.friStart.value;
  }

  public @Nullable Integer friEnd() {
    return this.friEnd.value;
  }

  public @Nullable Integer satStart() {
    return this.satStart.value;
  }

  public @Nullable Integer satEnd() {
    return this.satEnd.value;
  }

  public @Nullable Integer sunStart() {
    return this.sunStart.value;
  }

  public @Nullable Integer sunEnd() {
    return this.sunEnd.value;
  }

  public static Builder builder() {
    return new Builder();
  }

  public InputFieldMarshaller marshaller() {
    return new InputFieldMarshaller() {
      @Override
      public void marshal(InputFieldWriter writer) throws IOException {
        writer.writeCustom("id", com.fluentretail.graphql.type.CustomType.ID, id);
        if (allHours.defined) {
          writer.writeBoolean("allHours", allHours.value);
        }
        if (monStart.defined) {
          writer.writeInt("monStart", monStart.value);
        }
        if (monEnd.defined) {
          writer.writeInt("monEnd", monEnd.value);
        }
        if (tueStart.defined) {
          writer.writeInt("tueStart", tueStart.value);
        }
        if (tueEnd.defined) {
          writer.writeInt("tueEnd", tueEnd.value);
        }
        if (wedStart.defined) {
          writer.writeInt("wedStart", wedStart.value);
        }
        if (wedEnd.defined) {
          writer.writeInt("wedEnd", wedEnd.value);
        }
        if (thuStart.defined) {
          writer.writeInt("thuStart", thuStart.value);
        }
        if (thuEnd.defined) {
          writer.writeInt("thuEnd", thuEnd.value);
        }
        if (friStart.defined) {
          writer.writeInt("friStart", friStart.value);
        }
        if (friEnd.defined) {
          writer.writeInt("friEnd", friEnd.value);
        }
        if (satStart.defined) {
          writer.writeInt("satStart", satStart.value);
        }
        if (satEnd.defined) {
          writer.writeInt("satEnd", satEnd.value);
        }
        if (sunStart.defined) {
          writer.writeInt("sunStart", sunStart.value);
        }
        if (sunEnd.defined) {
          writer.writeInt("sunEnd", sunEnd.value);
        }
      }
    };
  }

  public static final class Builder {
    private @Nonnull String id;

    private Input<Boolean> allHours = Input.absent();

    private Input<Integer> monStart = Input.absent();

    private Input<Integer> monEnd = Input.absent();

    private Input<Integer> tueStart = Input.absent();

    private Input<Integer> tueEnd = Input.absent();

    private Input<Integer> wedStart = Input.absent();

    private Input<Integer> wedEnd = Input.absent();

    private Input<Integer> thuStart = Input.absent();

    private Input<Integer> thuEnd = Input.absent();

    private Input<Integer> friStart = Input.absent();

    private Input<Integer> friEnd = Input.absent();

    private Input<Integer> satStart = Input.absent();

    private Input<Integer> satEnd = Input.absent();

    private Input<Integer> sunStart = Input.absent();

    private Input<Integer> sunEnd = Input.absent();

    Builder() {
    }

    /**
     *  ID of the object
     */
    public Builder id(@Nonnull String id) {
      this.id = id;
      return this;
    }

    public Builder allHours(@Nullable Boolean allHours) {
      this.allHours = Input.fromNullable(allHours);
      return this;
    }

    public Builder monStart(@Nullable Integer monStart) {
      this.monStart = Input.fromNullable(monStart);
      return this;
    }

    public Builder monEnd(@Nullable Integer monEnd) {
      this.monEnd = Input.fromNullable(monEnd);
      return this;
    }

    public Builder tueStart(@Nullable Integer tueStart) {
      this.tueStart = Input.fromNullable(tueStart);
      return this;
    }

    public Builder tueEnd(@Nullable Integer tueEnd) {
      this.tueEnd = Input.fromNullable(tueEnd);
      return this;
    }

    public Builder wedStart(@Nullable Integer wedStart) {
      this.wedStart = Input.fromNullable(wedStart);
      return this;
    }

    public Builder wedEnd(@Nullable Integer wedEnd) {
      this.wedEnd = Input.fromNullable(wedEnd);
      return this;
    }

    public Builder thuStart(@Nullable Integer thuStart) {
      this.thuStart = Input.fromNullable(thuStart);
      return this;
    }

    public Builder thuEnd(@Nullable Integer thuEnd) {
      this.thuEnd = Input.fromNullable(thuEnd);
      return this;
    }

    public Builder friStart(@Nullable Integer friStart) {
      this.friStart = Input.fromNullable(friStart);
      return this;
    }

    public Builder friEnd(@Nullable Integer friEnd) {
      this.friEnd = Input.fromNullable(friEnd);
      return this;
    }

    public Builder satStart(@Nullable Integer satStart) {
      this.satStart = Input.fromNullable(satStart);
      return this;
    }

    public Builder satEnd(@Nullable Integer satEnd) {
      this.satEnd = Input.fromNullable(satEnd);
      return this;
    }

    public Builder sunStart(@Nullable Integer sunStart) {
      this.sunStart = Input.fromNullable(sunStart);
      return this;
    }

    public Builder sunEnd(@Nullable Integer sunEnd) {
      this.sunEnd = Input.fromNullable(sunEnd);
      return this;
    }

    public UpdateOpeningScheduleInput build() {
      Utils.checkNotNull(id, "id == null");
      return new UpdateOpeningScheduleInput(id, allHours, monStart, monEnd, tueStart, tueEnd, wedStart, wedEnd, thuStart, thuEnd, friStart, friEnd, satStart, satEnd, sunStart, sunEnd);
    }
  }
}
