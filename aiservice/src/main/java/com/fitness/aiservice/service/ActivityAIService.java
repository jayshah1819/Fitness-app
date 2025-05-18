package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {
    private final GeminiService geminiService;

    public String generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        log.info("RESPONSE FROM AI: {}", aiResponse);
        return aiResponse;
    }

    private String createPromptForActivity(Activity activity) {
        return String.format("""
        {
          "prompt": "Generate fitness insights for ALL activity types based on the following structure and activity-specific rules.",
          "response_format": {
            "ai_powered_recovery_tip": {
              "description": "Provide activity-specific physiological recovery advice.",
              "rules": [
                "Cite peer-reviewed studies published after 2020",
                "Include exact timing windows for recovery",
                "Use metric units only"
              ]
            },
            "smart_snack_pairing": {
              "description": "Provide two ready-to-eat food combinations.",
              "rules": [
                "Non-perishable items only",
                "Specify exact packaging (e.g. '200g pouch roasted almonds')",
                "Include macros per serving (g protein/carbs/fat)"
              ]
            },
            "metabolic_repair_mode": {
              "healthy_currency": "Nutrient-dense whole food equivalent",
              "cheat_currency": "Treat food equivalent",
              "recovery_boost": "Muscle-repair focused foods with timing"
            },
            "future_projections": {
              "description": "Provide 30-day impact projections if activity is repeated 3-5x/week.",
              "rules": [
                "Show body recomposition estimates",
                "Include performance metrics",
                "Reference exercise science principles"
              ]
            },
            "fun_facts": {
              "description": "Provide 3 verified facts per activity.",
              "rules": [
                "1 energy equivalence fact",
                "1 physiological adaptation fact",
                "1 historical/record fact"
              ]
            }
          },
          "activity_rules": {
            "RUNNING": {
              "priority_nutrients": ["complex carbs", "electrolytes"],
              "recovery_window": "30-45 mins post-run",
              "special_notes": "Impact forces require collagen support"
            },
            "WALKING": {
              "priority_nutrients": ["fiber", "polyphenols"],
              "recovery_window": "Flexible timing",
              "special_notes": "Focus on longevity benefits"
            },
            "CYCLING": {
              "priority_nutrients": ["nitrates", "carbs"],
              "recovery_window": "Immediate to 60 mins",
              "special_notes": "Aerobic efficiency focus"
            },
            "SWIMMING": {
              "priority_nutrients": ["sodium", "magnesium"],
              "recovery_window": "30-60 mins post-swim",
              "special_notes": "Cold water thermoregulation"
            },
            "WEIGHT_TRAINING": {
              "priority_nutrients": ["protein", "creatine"],
              "recovery_window": "Immediate to 30 mins",
              "special_notes": "Hypertrophy timing critical"
            },
            "YOGA": {
              "priority_nutrients": ["anti-inflammatories", "GABA"],
              "recovery_window": "Within 2 hours",
              "special_notes": "Nervous system recovery"
            },
            "HIIT": {
              "priority_nutrients": ["BCAAs", "fast carbs"],
              "recovery_window": "Immediate to 45 mins",
              "special_notes": "EPOC effect maximization"
            },
            "CARDIO": {
              "priority_nutrients": ["carbs", "electrolytes"],
              "recovery_window": "30 mins window",
              "special_notes": "Heart rate zone benefits"
            },
            "STRETCHING": {
              "priority_nutrients": ["collagen", "vitamin C"],
              "recovery_window": "Flexible timing",
              "special_notes": "Fascia hydration focus"
            },
            "OTHER": {
              "priority_nutrients": ["adaptogens", "balanced macros"],
              "recovery_window": "Case-by-case basis",
              "special_notes": "Customize based on intensity"
            }
          },
          "input_data": {
            "activity_type": "%s",
            "duration_minutes": %d,
            "calories_burned": %d
          },
          "examples": {
            "RUNNING": {
              "ai_powered_recovery_tip": "Consume 0.8g/kg carbs + 0.3g/kg protein within 30mins to replenish glycogen (JISSN 2023)",
              "smart_snack_pairing": {
                "option_1": {
                  "items": ["400g pouch roasted chickpeas", "250ml chocolate milk"],
                  "macros": "32g protein/78g carbs/12g fat"
                }
              },
              "fun_facts": [
                "Running economy improves 5%% per 100hrs of training",
                "500kcal run = energy to power a TV for 8 hours",
                "Eliud Kipchoge's marathon pace: 2:50/km for 42km"
              ]
            },
            "WEIGHT_TRAINING": {
              "ai_powered_recovery_tip": "20g whey protein + 3g creatine within 30mins maximizes MPS (Nutrition Reviews 2024)",
              "metabolic_repair_mode": {
                "recovery_boost": "500g cottage cheese + 50g honey (nighttime casein)"
              }
            }
          },
          "strict_rules": [
            "Never suggest raw meat/perishables",
            "All timestamps must convert to user's local timezone",
            "Macros must round to nearest gram",
            "Fun facts must include DOI/PMID when available"
          ]
        }
        """, activity.getType(), activity.getDuration(), activity.getCaloriesBurned());
    }

}

