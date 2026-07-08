package ph.edu.dlsu.lbycpob.quizmaster.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// UserProgress.java
public class UserProgress {
    private int totalQuestionsAnswered;
    private int correctAnswers;
    private int totalPoints;
    private Set<String> badges;
    private List<String> achievements;

    public UserProgress() {
        this.badges = new HashSet<>();
        this.achievements = new ArrayList<>();
    }

    public void addCorrectAnswer() {
        correctAnswers++;
        totalQuestionsAnswered++;
        totalPoints += 10;
        checkAchievements();
    }

    public void addIncorrectAnswer() {
        totalQuestionsAnswered++;
        checkAchievements();
    }

    private void checkAchievements() {
        // First Question Badge
        if (totalQuestionsAnswered == 1 && !badges.contains("First Steps")) {
            badges.add("First Steps");
            achievements.add(" First Steps: Answered your first question!");
        }

        // Perfect Score Badge
        if (correctAnswers >= 5 && getAccuracy() == 100.0 && !badges.contains("Perfect")) {
            badges.add("Perfect");
            achievements.add(" Perfect: 100% accuracy with 5+ questions!");
        }

        // Speed Badge
        if (totalQuestionsAnswered >= 10 && !badges.contains("Quick Learner")) {
            badges.add("Quick Learner");
            achievements.add("⚡ Quick Learner: Answered 10 questions!");
        }

        // Points Milestone
        if (totalPoints >= 100 && !badges.contains("Century")) {
            badges.add("Century");
            achievements.add(" Century: Reached 100 points!");
        }
    }

    public double getAccuracy() {
        return totalQuestionsAnswered == 0 ? 0 : (correctAnswers * 100.0) / totalQuestionsAnswered;
    }

    public String getRecommendation() {
        double accuracy = getAccuracy();
        if (accuracy >= 90) {
            return "Excellent! Try advanced topics or time challenges.";
        } else if (accuracy >= 70) {
            return "Good work! Focus on reviewing incorrect answers.";
        } else if (accuracy >= 50) {
            return "Keep practicing! Review fundamentals in weak areas.";
        } else {
            return "Consider reviewing basic concepts before continuing.";
        }
    }

    // Getters
    public int getTotalQuestionsAnswered() { return totalQuestionsAnswered; }
    public int getCorrectAnswers() { return correctAnswers; }
    public int getTotalPoints() { return totalPoints; }
    public Set<String> getBadges() { return badges; }
    public List<String> getAchievements() { return achievements; }
    public void clearRecentAchievements() { achievements.clear(); }
}

