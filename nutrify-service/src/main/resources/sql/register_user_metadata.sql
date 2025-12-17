INSERT INTO user_metadata (
    user_id, height, weight, age, gender, activity_level, goal, notes
)
VALUES (?, ?, ?, ?, ?, ?, ?, ?)
RETURNING id;