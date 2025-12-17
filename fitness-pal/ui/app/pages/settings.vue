<script setup lang="ts">
import * as z from "zod";
import type { FormSubmitEvent } from "@nuxt/ui";

const toast = useToast();

const {
    settings: userSettings,
    updateProfile,
    updatePreferences,
    updateWorkout,
    updateNotifications,
    updatePrivacy,
} = useUserSettings();

const activeTab = ref(0);

const themeOptions = [
    { label: "Light", value: "light", icon: "i-lucide-sun" },
    { label: "Dark", value: "dark", icon: "i-lucide-moon" },
    { label: "System", value: "system", icon: "i-lucide-monitor" },
];

const languageOptions = [
    { label: "English", value: "en" },
    { label: "Spanish", value: "es" },
    { label: "French", value: "fr" },
    { label: "German", value: "de" },
];

const unitsOptions = [
    { label: "Metric (kg, cm)", value: "metric" },
    { label: "Imperial (lb, in)", value: "imperial" },
];

const defaultViewOptions = [
    { label: "Daily", value: "daily" },
    { label: "Weekly", value: "weekly" },
    { label: "Monthly", value: "monthly" },
];

const privacyOptions = [
    { label: "Public", value: "public" },
    { label: "Friends Only", value: "friends" },
    { label: "Private", value: "private" },
];

const profileSchema = z.object({
    name: z.string().min(2, "Name must be at least 2 characters"),
    email: z.string().email("Invalid email address"),
    bio: z.string().max(200, "Bio must be less than 200 characters").optional(),
});

type ProfileSchema = z.output<typeof profileSchema>;

function onProfileSubmit(event: FormSubmitEvent<ProfileSchema>) {
    updateProfile(event.data);
    toast.add({
        title: "Profile Updated",
        description: "Your profile has been successfully updated.",
        color: "success",
        icon: "i-lucide-check-circle",
    });
}

function savePreferences() {
    updatePreferences(userSettings.value.preferences);
    toast.add({
        title: "Preferences Saved",
        description: "Your preferences have been successfully updated.",
        color: "success",
        icon: "i-lucide-check-circle",
    });
}

function saveWorkoutSettings() {
    updateWorkout(userSettings.value.workout);
    toast.add({
        title: "Workout Settings Updated",
        description: "Your workout preferences have been saved.",
        color: "success",
        icon: "i-lucide-check-circle",
    });
}

function saveNotificationSettings() {
    updateNotifications(userSettings.value.notifications);
    toast.add({
        title: "Notifications Updated",
        description: "Your notification preferences have been saved.",
        color: "success",
        icon: "i-lucide-check-circle",
    });
}

function savePrivacySettings() {
    updatePrivacy(userSettings.value.privacy);
    toast.add({
        title: "Privacy Settings Updated",
        description: "Your privacy preferences have been saved.",
        color: "success",
        icon: "i-lucide-check-circle",
    });
}

const tabs = [
    {
        label: "Profile",
        icon: "i-lucide-user",
    },
    {
        label: "Preferences",
        icon: "i-lucide-settings",
    },
    {
        label: "Workout",
        icon: "i-lucide-dumbbell",
    },
    {
        label: "Notifications",
        icon: "i-lucide-bell",
    },
    {
        label: "Privacy",
        icon: "i-lucide-shield",
    },
];
</script>

<template>
    <UDashboardPanel id="settings">
        <template #header>
            <UDashboardNavbar title="Settings" :ui="{ right: 'gap-3' }">
                <template #leading>
                    <UDashboardSidebarCollapse />
                </template>
            </UDashboardNavbar>
        </template>

        <div class="p-6 max-w-5xl mx-auto">
            <UTabs v-model="activeTab" :items="tabs" class="mb-8" />

            <div v-if="activeTab === 0" class="space-y-6">
                <div>
                    <h2 class="text-2xl font-bold mb-2">Profile Settings</h2>
                    <p class="text-gray-500 dark:text-gray-400">
                        Manage your profile information and public presence.
                    </p>
                </div>

                <UCard>
                    <UForm
                        :schema="profileSchema"
                        :state="userSettings.profile"
                        class="space-y-4"
                        @submit="onProfileSubmit"
                    >
                        <UFormGroup label="Full Name" name="name" required>
                            <UInput
                                v-model="userSettings.profile.name"
                                placeholder="Enter your full name"
                                icon="i-lucide-user"
                            />
                        </UFormGroup>

                        <UFormGroup label="Email Address" name="email" required>
                            <UInput
                                v-model="userSettings.profile.email"
                                type="email"
                                placeholder="your.email@example.com"
                                icon="i-lucide-mail"
                            />
                        </UFormGroup>

                        <UFormGroup label="Bio" name="bio">
                            <UTextarea
                                v-model="userSettings.profile.bio"
                                placeholder="Tell us about yourself..."
                                :rows="4"
                            />
                        </UFormGroup>

                        <div class="flex justify-end">
                            <UButton type="submit" color="primary">
                                Save Profile
                            </UButton>
                        </div>
                    </UForm>
                </UCard>
            </div>

            <div v-else-if="activeTab === 1" class="space-y-6">
                <div>
                    <h2 class="text-2xl font-bold mb-2">General Preferences</h2>
                    <p class="text-gray-500 dark:text-gray-400">
                        Customize your app experience with theme, language, and
                        display settings.
                    </p>
                </div>

                <UCard>
                    <div class="space-y-6">
                        <UFormGroup
                            label="Theme"
                            description="Choose your preferred color theme"
                        >
                            <URadioGroup
                                v-model="userSettings.preferences.theme"
                                :options="themeOptions"
                                class="flex gap-4"
                            />
                        </UFormGroup>

                        <UDivider />

                        <UFormGroup
                            label="Language"
                            description="Select your preferred language"
                        >
                            <USelect
                                v-model="userSettings.preferences.language"
                                :options="languageOptions"
                                icon="i-lucide-globe"
                            />
                        </UFormGroup>

                        <UDivider />

                        <UFormGroup
                            label="Timezone"
                            description="Your local timezone"
                        >
                            <UInput
                                v-model="userSettings.preferences.timezone"
                                icon="i-lucide-clock"
                                disabled
                            />
                        </UFormGroup>

                        <div class="flex justify-end pt-4">
                            <UButton color="primary" @click="savePreferences">
                                Save Preferences
                            </UButton>
                        </div>
                    </div>
                </UCard>
            </div>

            <div v-else-if="activeTab === 2" class="space-y-6">
                <div>
                    <h2 class="text-2xl font-bold mb-2">Workout Settings</h2>
                    <p class="text-gray-500 dark:text-gray-400">
                        Configure your workout tracking preferences and
                        defaults.
                    </p>
                </div>

                <UCard>
                    <div class="space-y-6">
                        <UFormGroup
                            label="Units"
                            description="Choose your preferred measurement system"
                        >
                            <USelect
                                v-model="userSettings.workout.units"
                                :options="unitsOptions"
                                icon="i-lucide-ruler"
                            />
                        </UFormGroup>

                        <UDivider />

                        <UFormGroup
                            label="Default View"
                            description="Default time period for workout statistics"
                        >
                            <USelect
                                v-model="userSettings.workout.defaultView"
                                :options="defaultViewOptions"
                                icon="i-lucide-calendar"
                            />
                        </UFormGroup>

                        <UDivider />

                        <div class="space-y-4">
                            <h3 class="font-semibold">Rest Timer Settings</h3>

                            <UFormGroup>
                                <div class="flex items-center justify-between">
                                    <div class="flex-1">
                                        <label class="font-medium"
                                            >Sound Alerts</label
                                        >
                                        <p
                                            class="text-sm text-gray-500 dark:text-gray-400"
                                        >
                                            Play sound when rest timer completes
                                        </p>
                                    </div>
                                    <UToggle
                                        v-model="
                                            userSettings.workout.restTimerSound
                                        "
                                    />
                                </div>
                            </UFormGroup>

                            <UFormGroup>
                                <div class="flex items-center justify-between">
                                    <div class="flex-1">
                                        <label class="font-medium"
                                            >Auto-start Rest Timer</label
                                        >
                                        <p
                                            class="text-sm text-gray-500 dark:text-gray-400"
                                        >
                                            Automatically start rest timer after
                                            completing a set
                                        </p>
                                    </div>
                                    <UToggle
                                        v-model="
                                            userSettings.workout.autoStartRest
                                        "
                                    />
                                </div>
                            </UFormGroup>
                        </div>

                        <div class="flex justify-end pt-4">
                            <UButton
                                color="primary"
                                @click="saveWorkoutSettings"
                            >
                                Save Workout Settings
                            </UButton>
                        </div>
                    </div>
                </UCard>
            </div>

            <div v-else-if="activeTab === 3" class="space-y-6">
                <div>
                    <h2 class="text-2xl font-bold mb-2">
                        Notification Settings
                    </h2>
                    <p class="text-gray-500 dark:text-gray-400">
                        Manage how and when you receive notifications.
                    </p>
                </div>

                <UCard>
                    <div class="space-y-6">
                        <div class="space-y-4">
                            <h3 class="font-semibold">Notification Channels</h3>

                            <UFormGroup>
                                <div class="flex items-center justify-between">
                                    <div class="flex-1">
                                        <label class="font-medium"
                                            >Email Notifications</label
                                        >
                                        <p
                                            class="text-sm text-gray-500 dark:text-gray-400"
                                        >
                                            Receive notifications via email
                                        </p>
                                    </div>
                                    <UToggle
                                        v-model="
                                            userSettings.notifications.email
                                        "
                                    />
                                </div>
                            </UFormGroup>

                            <UFormGroup>
                                <div class="flex items-center justify-between">
                                    <div class="flex-1">
                                        <label class="font-medium"
                                            >Push Notifications</label
                                        >
                                        <p
                                            class="text-sm text-gray-500 dark:text-gray-400"
                                        >
                                            Receive push notifications in your
                                            browser
                                        </p>
                                    </div>
                                    <UToggle
                                        v-model="
                                            userSettings.notifications.push
                                        "
                                    />
                                </div>
                            </UFormGroup>
                        </div>

                        <UDivider />

                        <div class="space-y-4">
                            <h3 class="font-semibold">Notification Types</h3>

                            <UFormGroup>
                                <div class="flex items-center justify-between">
                                    <div class="flex-1">
                                        <label class="font-medium"
                                            >Workout Reminders</label
                                        >
                                        <p
                                            class="text-sm text-gray-500 dark:text-gray-400"
                                        >
                                            Get reminded about scheduled
                                            workouts
                                        </p>
                                    </div>
                                    <UToggle
                                        v-model="
                                            userSettings.notifications
                                                .workoutReminders
                                        "
                                    />
                                </div>
                            </UFormGroup>

                            <UFormGroup>
                                <div class="flex items-center justify-between">
                                    <div class="flex-1">
                                        <label class="font-medium"
                                            >Weekly Reports</label
                                        >
                                        <p
                                            class="text-sm text-gray-500 dark:text-gray-400"
                                        >
                                            Receive weekly workout summary
                                            reports
                                        </p>
                                    </div>
                                    <UToggle
                                        v-model="
                                            userSettings.notifications
                                                .weeklyReport
                                        "
                                    />
                                </div>
                            </UFormGroup>

                            <UFormGroup>
                                <div class="flex items-center justify-between">
                                    <div class="flex-1">
                                        <label class="font-medium"
                                            >Achievement Alerts</label
                                        >
                                        <p
                                            class="text-sm text-gray-500 dark:text-gray-400"
                                        >
                                            Get notified when you unlock
                                            achievements
                                        </p>
                                    </div>
                                    <UToggle
                                        v-model="
                                            userSettings.notifications
                                                .achievementAlerts
                                        "
                                    />
                                </div>
                            </UFormGroup>
                        </div>

                        <div class="flex justify-end pt-4">
                            <UButton
                                color="primary"
                                @click="saveNotificationSettings"
                            >
                                Save Notification Settings
                            </UButton>
                        </div>
                    </div>
                </UCard>
            </div>

            <div v-else-if="activeTab === 4" class="space-y-6">
                <div>
                    <h2 class="text-2xl font-bold mb-2">Privacy & Security</h2>
                    <p class="text-gray-500 dark:text-gray-400">
                        Control who can see your profile and workout data.
                    </p>
                </div>

                <UCard>
                    <div class="space-y-6">
                        <UFormGroup
                            label="Profile Visibility"
                            description="Control who can view your profile"
                        >
                            <USelect
                                v-model="userSettings.privacy.profileVisibility"
                                :options="privacyOptions"
                                icon="i-lucide-eye"
                            />
                        </UFormGroup>

                        <UDivider />

                        <div class="space-y-4">
                            <h3 class="font-semibold">Data Sharing</h3>

                            <UFormGroup>
                                <div class="flex items-center justify-between">
                                    <div class="flex-1">
                                        <label class="font-medium"
                                            >Show Workouts</label
                                        >
                                        <p
                                            class="text-sm text-gray-500 dark:text-gray-400"
                                        >
                                            Allow others to see your workout
                                            history
                                        </p>
                                    </div>
                                    <UToggle
                                        v-model="
                                            userSettings.privacy.showWorkouts
                                        "
                                    />
                                </div>
                            </UFormGroup>

                            <UFormGroup>
                                <div class="flex items-center justify-between">
                                    <div class="flex-1">
                                        <label class="font-medium"
                                            >Show Statistics</label
                                        >
                                        <p
                                            class="text-sm text-gray-500 dark:text-gray-400"
                                        >
                                            Allow others to see your workout
                                            statistics
                                        </p>
                                    </div>
                                    <UToggle
                                        v-model="userSettings.privacy.showStats"
                                    />
                                </div>
                            </UFormGroup>
                        </div>

                        <UDivider />

                        <div class="space-y-4">
                            <h3
                                class="font-semibold text-red-600 dark:text-red-400"
                            >
                                Danger Zone
                            </h3>

                            <div
                                class="p-4 bg-red-50 dark:bg-red-900/20 rounded-lg border border-red-200 dark:border-red-800"
                            >
                                <h4 class="font-medium mb-2">Delete Account</h4>
                                <p
                                    class="text-sm text-gray-600 dark:text-gray-400 mb-4"
                                >
                                    Permanently delete your account and all
                                    associated data. This action cannot be
                                    undone.
                                </p>
                                <UButton color="error" variant="outline">
                                    Delete Account
                                </UButton>
                            </div>
                        </div>

                        <div class="flex justify-end pt-4">
                            <UButton
                                color="primary"
                                @click="savePrivacySettings"
                            >
                                Save Privacy Settings
                            </UButton>
                        </div>
                    </div>
                </UCard>
            </div>
        </div>
    </UDashboardPanel>
</template>
