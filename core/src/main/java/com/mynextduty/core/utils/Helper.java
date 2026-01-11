package com.mynextduty.core.utils;

import com.mynextduty.core.enums.LifeStage;
import lombok.NonNull;

public class Helper {
  private Helper() {}

  @NonNull
  public static LifeStage getLifeStage(int age) {
    if (age < 30) {
      return LifeStage.EARLY_CAREER;
    } else if (age < 35) {
      return LifeStage.CAREER_BUILDING;
    } else if (age < 45) {
      return LifeStage.FAMILY_BUILDING;
    } else if (age < 55) {
      return LifeStage.MID_CAREER;
    } else if (age < 65) {
      return LifeStage.PRE_RETIREMENT;
    } else if (age < 75) {
      return LifeStage.RETIREMENT;
    } else {
      return LifeStage.SENIOR;
    }
  }
}
