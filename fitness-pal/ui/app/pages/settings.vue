<script setup lang="ts">
import * as z from "zod";
import { toTypedSchema } from '@vee-validate/zod'
import { useForm, Field as FormField } from 'vee-validate'
import { ActivityLevel, PrimaryGoal, type ProfileSetupFormData } from "~/types";
import Button from '~/components/ui/button/Button.vue'
import Card from '~/components/ui/card/Card.vue'
import CardContent from '~/components/ui/card/CardContent.vue'
import Input from '~/components/ui/input/Input.vue'
import Textarea from '~/components/ui/textarea/Textarea.vue'
import Tabs from '~/components/ui/tabs/Tabs.vue'
import TabsList from '~/components/ui/tabs/TabsList.vue'
import TabsTrigger from '~/components/ui/tabs/TabsTrigger.vue'
import TabsContent from '~/components/ui/tabs/TabsContent.vue'
import Select from '~/components/ui/select/Select.vue'
import SelectContent from '~/components/ui/select/SelectContent.vue'
import SelectItem from '~/components/ui/select/SelectItem.vue'
import SelectTrigger from '~/components/ui/select/SelectTrigger.vue'
import SelectValue from '~/components/ui/select/SelectValue.vue'
import RadioGroup from '~/components/ui/radio-group/RadioGroup.vue'
import RadioGroupItem from '~/components/ui/radio-group/RadioGroupItem.vue'
import Switch from '~/components/ui/switch/Switch.vue'
import Separator from '~/components/ui/separator/Separator.vue'
import Label from '~/components/ui/label/Label.vue'
import FormItem from '~/components/ui/form/FormItem.vue'
import FormLabel from '~/components/ui/form/FormLabel.vue'
import FormControl from '~/components/ui/form/FormControl.vue'
import FormMessage from '~/components/ui/form/FormMessage.vue'
import { User, Activity, Settings, Dumbbell, Bell, Shield, Sun, Moon, Monitor, Flame, Repeat, Zap, HeartPulse, ShieldCheck, Globe, Clock, Ruler, Calendar, Eye } from 'lucide-vue-next'

const toast = useToast();

const {
    settings: userSettings,
    updateProfile,
    updatePreferences,
    updateWorkout,
    updateNotifications,
    updatePrivacy,
} = useUserSettings();

const { fetchProfile, createProfile, updateProfile: updateFitnessProfile } = useUserProfile();
const userStore = useUserStore();

const activeTab = ref('profile');

const themeOptions = [
    { label: "Light", value: "light", icon: Sun },
    { label: "Dark", value: "dark", icon: Moon },
    { label: "System", value: "system", icon: Monitor },
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

const profileSchema = toTypedSchema(z.object({
    name: z.string().min(2, "Name must be at least 2 characters"),
    email: z.string().email("Invalid email address"),
    bio: z.string().max(200, "Bio must be less than 200 characters").optional(),
}));

const { handleSubmit: handleProfileSubmit, resetForm } = useForm({
    validationSchema: profileSchema,
    initialValues: {
        name: userSettings.value.profile.name,
        email: userSettings.value.profile.email,
        bio: userSettings.value.profile.bio,
    }
});

const onProfileSubmit = handleProfileSubmit((values) => {
    updateProfile(values);
    toast.add({
        title: "Profile Updated",
        description: "Your profile has been successfully updated.",
        color: "success",
    });
});

function savePreferences() {
    updatePreferences(userSettings.value.preferences);
    toast.add({
        title: "Preferences Saved",
        description: "Your preferences have been successfully updated.",
        color: "success",
    });
}

function saveWorkoutSettings() {
    updateWorkout(userSettings.value.workout);
    toast.add({
        title: "Workout Settings Updated",
        description: "Your workout preferences have been saved.",
        color: "success",
    });
}

function saveNotificationSettings() {
    updateNotifications(userSettings.value.notifications);
    toast.add({
        title: "Notifications Updated",
        description: "Your notification preferences have been saved.",
        color: "success",
    });
}

function savePrivacySettings() {
    updatePrivacy(userSettings.value.privacy);
    toast.add({
        title: "Privacy Settings Updated",
        description: "Your privacy preferences have been saved.",
        color: "success",
    });
}

const fitnessProfileState = reactive({
    height: 66,
    weight: 150,
    gender: true,
    activity_level: ActivityLevel.MODERATELY_ACTIVE,
    goal: PrimaryGoal.GENERAL_HEALTH
});

const activityLevels = [
    { value: ActivityLevel.SEDENTARY, label: 'Sedentary', description: 'Little to no exercise' },
    { value: ActivityLevel.LIGHTLY_ACTIVE, label: 'Lightly Active', description: 'Exercise 1-3 days/week' },
    { value: ActivityLevel.MODERATELY_ACTIVE, label: 'Moderately Active', description: 'Exercise 3-5 days/week' },
    { value: ActivityLevel.VERY_ACTIVE, label: 'Very Active', description: 'Exercise 6-7 days/week' },
    { value: ActivityLevel.EXTREMELY_ACTIVE, label: 'Extremely Active', description: 'Physical job or training twice/day' }
];

const goals = [
    { value: PrimaryGoal.FAT_LOSS, label: 'Fat Loss', icon: Flame, description: 'Reduce body fat percentage' },
    { value: PrimaryGoal.MUSCLE_GAIN, label: 'Muscle Gain', icon: Dumbbell, description: 'Build muscle mass and size' },
    { value: PrimaryGoal.RECOMPOSITION, label: 'Recomposition', icon: Repeat, description: 'Lose fat while gaining muscle' },
    { value: PrimaryGoal.STRENGTH, label: 'Strength', icon: Zap, description: 'Increase overall strength' },
    { value: PrimaryGoal.ENDURANCE, label: 'Endurance', icon: HeartPulse, description: 'Improve stamina and endurance' },
    { value: PrimaryGoal.GENERAL_HEALTH, label: 'General Health', icon: ShieldCheck, description: 'Maintain overall wellness' }
];

onMounted(async () => {
    // TODO: Load fitness profile on mount
});

async function saveFitnessProfile() {
    const profileData: ProfileSetupFormData = {
        height: fitnessProfileState.height,
        weight: fitnessProfileState.weight,
        gender: fitnessProfileState.gender,
        activity_level: fitnessProfileState.activity_level,
        goal: fitnessProfileState.goal
    };

    const result = await updateFitnessProfile(profileData);

    if (result) {
        toast.add({
            title: "Fitness Profile Updated",
            description: "Your fitness profile has been successfully updated.",
            color: "success",
        });
    } else {
        toast.add({
            title: "Error",
            description: "Failed to update fitness profile.",
            color: "error",
        });
    }
}

const tabs = [
    { value: "profile", label: "Profile", icon: User },
    { value: "fitness", label: "Fitness Profile", icon: Activity },
    { value: "preferences", label: "Preferences", icon: Settings },
    { value: "workout", label: "Workout", icon: Dumbbell },
    { value: "notifications", label: "Notifications", icon: Bell },
    { value: "privacy", label: "Privacy", icon: Shield },
];
</script>

<template>
    <div class="flex-1 flex flex-col h-full">
        <!-- Header -->
        <header class="flex h-16 items-center justify-between border-b px-6 shrink-0">
            <h1 class="text-xl font-semibold">Settings</h1>
        </header>

        <!-- Body -->
        <div class="flex-1 overflow-auto p-6">
            <div class="max-w-5xl mx-auto">
                <Tabs v-model="activeTab" class="w-full">
                    <TabsList class="mb-8 w-full justify-start flex-wrap h-auto gap-1">
                        <TabsTrigger
                            v-for="tab in tabs"
                            :key="tab.value"
                            :value="tab.value"
                            class="flex items-center gap-2"
                        >
                            <component :is="tab.icon" class="h-4 w-4" />
                            {{ tab.label }}
                        </TabsTrigger>
                    </TabsList>

                    <!-- Profile Tab -->
                    <TabsContent value="profile" class="space-y-6">
                        <div>
                            <h2 class="text-2xl font-bold mb-2">Profile Settings</h2>
                            <p class="text-gray-500 dark:text-gray-400">
                                Manage your profile information and public presence.
                            </p>
                        </div>

                        <Card>
                            <CardContent class="p-6">
                                <form class="space-y-4" @submit="onProfileSubmit">
                                    <FormField v-slot="{ componentField }" name="name">
                                        <FormItem>
                                            <FormLabel>Full Name *</FormLabel>
                                            <FormControl>
                                                <div class="relative">
                                                    <User class="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                                                    <Input
                                                        v-bind="componentField"
                                                        placeholder="Enter your full name"
                                                        class="pl-10"
                                                    />
                                                </div>
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    </FormField>

                                    <FormField v-slot="{ componentField }" name="email">
                                        <FormItem>
                                            <FormLabel>Email Address *</FormLabel>
                                            <FormControl>
                                                <Input
                                                    v-bind="componentField"
                                                    type="email"
                                                    placeholder="your.email@example.com"
                                                />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    </FormField>

                                    <FormField v-slot="{ componentField }" name="bio">
                                        <FormItem>
                                            <FormLabel>Bio</FormLabel>
                                            <FormControl>
                                                <Textarea
                                                    v-bind="componentField"
                                                    placeholder="Tell us about yourself..."
                                                    :rows="4"
                                                />
                                            </FormControl>
                                            <FormMessage />
                                        </FormItem>
                                    </FormField>

                                    <div class="flex justify-end">
                                        <Button type="submit">
                                            Save Profile
                                        </Button>
                                    </div>
                                </form>
                            </CardContent>
                        </Card>
                    </TabsContent>

                    <!-- Fitness Profile Tab -->
                    <TabsContent value="fitness" class="space-y-6">
                        <div>
                            <h2 class="text-2xl font-bold mb-2">Fitness Profile</h2>
                            <p class="text-gray-500 dark:text-gray-400">
                                Manage your fitness goals and personal metrics.
                            </p>
                        </div>

                        <Card>
                            <CardContent class="p-6 space-y-6">
                                <div class="space-y-2">
                                    <Label>Height (inches)</Label>
                                    <p class="text-sm text-muted-foreground">Your height in inches</p>
                                    <div class="relative">
                                        <Ruler class="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                                        <Input
                                            v-model.number="fitnessProfileState.height"
                                            type="number"
                                            placeholder="66"
                                            class="pl-10"
                                        />
                                    </div>
                                </div>

                                <Separator />

                                <div class="space-y-2">
                                    <Label>Weight (lbs)</Label>
                                    <p class="text-sm text-muted-foreground">Your current weight in pounds</p>
                                    <Input
                                        v-model.number="fitnessProfileState.weight"
                                        type="number"
                                        placeholder="150"
                                    />
                                </div>

                                <Separator />

                                <div class="space-y-2">
                                    <Label>Gender</Label>
                                    <p class="text-sm text-muted-foreground">Select your gender</p>
                                    <div class="flex gap-4">
                                        <Button
                                            :variant="fitnessProfileState.gender === true ? 'default' : 'outline'"
                                            @click="fitnessProfileState.gender = true"
                                        >
                                            Male
                                        </Button>
                                        <Button
                                            :variant="fitnessProfileState.gender === false ? 'default' : 'outline'"
                                            @click="fitnessProfileState.gender = false"
                                        >
                                            Female
                                        </Button>
                                    </div>
                                </div>

                                <Separator />

                                <div class="space-y-3">
                                    <Label>Activity Level</Label>
                                    <p class="text-sm text-muted-foreground">How active are you on a typical week?</p>
                                    <RadioGroup v-model="fitnessProfileState.activity_level" class="space-y-2">
                                        <div
                                            v-for="level in activityLevels"
                                            :key="level.value"
                                            class="flex items-start space-x-3"
                                        >
                                            <RadioGroupItem :value="level.value" :id="level.value" />
                                            <Label :for="level.value" class="font-normal cursor-pointer">
                                                <p class="font-medium">{{ level.label }}</p>
                                                <p class="text-sm text-gray-500 dark:text-gray-400">{{ level.description }}</p>
                                            </Label>
                                        </div>
                                    </RadioGroup>
                                </div>

                                <Separator />

                                <div class="space-y-3">
                                    <Label>Fitness Goal</Label>
                                    <p class="text-sm text-muted-foreground">What do you want to achieve?</p>
                                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                                        <button
                                            v-for="goal in goals"
                                            :key="goal.value"
                                            type="button"
                                            class="p-4 rounded-lg border-2 transition-all text-left"
                                            :class="fitnessProfileState.goal === goal.value
                                                ? 'border-gray-900 bg-gray-50 dark:border-white dark:bg-gray-800'
                                                : 'border-gray-200 dark:border-gray-700 hover:border-gray-300 dark:hover:border-gray-600'"
                                            @click="fitnessProfileState.goal = goal.value"
                                        >
                                            <component :is="goal.icon" class="w-6 h-6 mb-2" />
                                            <h3 class="font-bold mb-1">{{ goal.label }}</h3>
                                            <p class="text-sm text-gray-600 dark:text-gray-400">{{ goal.description }}</p>
                                        </button>
                                    </div>
                                </div>

                                <div class="flex justify-end pt-4">
                                    <Button @click="saveFitnessProfile">
                                        Save Fitness Profile
                                    </Button>
                                </div>
                            </CardContent>
                        </Card>
                    </TabsContent>

                    <!-- Preferences Tab -->
                    <TabsContent value="preferences" class="space-y-6">
                        <div>
                            <h2 class="text-2xl font-bold mb-2">General Preferences</h2>
                            <p class="text-gray-500 dark:text-gray-400">
                                Customize your app experience with theme, language, and display settings.
                            </p>
                        </div>

                        <Card>
                            <CardContent class="p-6 space-y-6">
                                <div class="space-y-3">
                                    <Label>Theme</Label>
                                    <p class="text-sm text-muted-foreground">Choose your preferred color theme</p>
                                    <div class="flex gap-4">
                                        <Button
                                            v-for="option in themeOptions"
                                            :key="option.value"
                                            :variant="userSettings.preferences.theme === option.value ? 'default' : 'outline'"
                                            @click="userSettings.preferences.theme = option.value"
                                        >
                                            <component :is="option.icon" class="h-4 w-4 mr-2" />
                                            {{ option.label }}
                                        </Button>
                                    </div>
                                </div>

                                <Separator />

                                <div class="space-y-2">
                                    <Label>Language</Label>
                                    <p class="text-sm text-muted-foreground">Select your preferred language</p>
                                    <Select v-model="userSettings.preferences.language">
                                        <SelectTrigger class="w-full max-w-xs">
                                            <Globe class="h-4 w-4 mr-2" />
                                            <SelectValue placeholder="Select language" />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem
                                                v-for="option in languageOptions"
                                                :key="option.value"
                                                :value="option.value"
                                            >
                                                {{ option.label }}
                                            </SelectItem>
                                        </SelectContent>
                                    </Select>
                                </div>

                                <Separator />

                                <div class="space-y-2">
                                    <Label>Timezone</Label>
                                    <p class="text-sm text-muted-foreground">Your local timezone</p>
                                    <div class="relative max-w-xs">
                                        <Clock class="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                                        <Input
                                            v-model="userSettings.preferences.timezone"
                                            class="pl-10"
                                            disabled
                                        />
                                    </div>
                                </div>

                                <div class="flex justify-end pt-4">
                                    <Button @click="savePreferences">
                                        Save Preferences
                                    </Button>
                                </div>
                            </CardContent>
                        </Card>
                    </TabsContent>

                    <!-- Workout Tab -->
                    <TabsContent value="workout" class="space-y-6">
                        <div>
                            <h2 class="text-2xl font-bold mb-2">Workout Settings</h2>
                            <p class="text-gray-500 dark:text-gray-400">
                                Configure your workout tracking preferences and defaults.
                            </p>
                        </div>

                        <Card>
                            <CardContent class="p-6 space-y-6">
                                <div class="space-y-2">
                                    <Label>Units</Label>
                                    <p class="text-sm text-muted-foreground">Choose your preferred measurement system</p>
                                    <Select v-model="userSettings.workout.units">
                                        <SelectTrigger class="w-full max-w-xs">
                                            <Ruler class="h-4 w-4 mr-2" />
                                            <SelectValue placeholder="Select units" />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem
                                                v-for="option in unitsOptions"
                                                :key="option.value"
                                                :value="option.value"
                                            >
                                                {{ option.label }}
                                            </SelectItem>
                                        </SelectContent>
                                    </Select>
                                </div>

                                <Separator />

                                <div class="space-y-2">
                                    <Label>Default View</Label>
                                    <p class="text-sm text-muted-foreground">Default time period for workout statistics</p>
                                    <Select v-model="userSettings.workout.defaultView">
                                        <SelectTrigger class="w-full max-w-xs">
                                            <Calendar class="h-4 w-4 mr-2" />
                                            <SelectValue placeholder="Select view" />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem
                                                v-for="option in defaultViewOptions"
                                                :key="option.value"
                                                :value="option.value"
                                            >
                                                {{ option.label }}
                                            </SelectItem>
                                        </SelectContent>
                                    </Select>
                                </div>

                                <Separator />

                                <div class="space-y-4">
                                    <h3 class="font-semibold">Rest Timer Settings</h3>

                                    <div class="flex items-center justify-between">
                                        <div class="flex-1">
                                            <Label>Sound Alerts</Label>
                                            <p class="text-sm text-gray-500 dark:text-gray-400">
                                                Play sound when rest timer completes
                                            </p>
                                        </div>
                                        <Switch v-model:checked="userSettings.workout.restTimerSound" />
                                    </div>

                                    <div class="flex items-center justify-between">
                                        <div class="flex-1">
                                            <Label>Auto-start Rest Timer</Label>
                                            <p class="text-sm text-gray-500 dark:text-gray-400">
                                                Automatically start rest timer after completing a set
                                            </p>
                                        </div>
                                        <Switch v-model:checked="userSettings.workout.autoStartRest" />
                                    </div>
                                </div>

                                <div class="flex justify-end pt-4">
                                    <Button @click="saveWorkoutSettings">
                                        Save Workout Settings
                                    </Button>
                                </div>
                            </CardContent>
                        </Card>
                    </TabsContent>

                    <!-- Notifications Tab -->
                    <TabsContent value="notifications" class="space-y-6">
                        <div>
                            <h2 class="text-2xl font-bold mb-2">Notification Settings</h2>
                            <p class="text-gray-500 dark:text-gray-400">
                                Manage how and when you receive notifications.
                            </p>
                        </div>

                        <Card>
                            <CardContent class="p-6 space-y-6">
                                <div class="space-y-4">
                                    <h3 class="font-semibold">Notification Channels</h3>

                                    <div class="flex items-center justify-between">
                                        <div class="flex-1">
                                            <Label>Email Notifications</Label>
                                            <p class="text-sm text-gray-500 dark:text-gray-400">
                                                Receive notifications via email
                                            </p>
                                        </div>
                                        <Switch v-model:checked="userSettings.notifications.email" />
                                    </div>

                                    <div class="flex items-center justify-between">
                                        <div class="flex-1">
                                            <Label>Push Notifications</Label>
                                            <p class="text-sm text-gray-500 dark:text-gray-400">
                                                Receive push notifications in your browser
                                            </p>
                                        </div>
                                        <Switch v-model:checked="userSettings.notifications.push" />
                                    </div>
                                </div>

                                <Separator />

                                <div class="space-y-4">
                                    <h3 class="font-semibold">Notification Types</h3>

                                    <div class="flex items-center justify-between">
                                        <div class="flex-1">
                                            <Label>Workout Reminders</Label>
                                            <p class="text-sm text-gray-500 dark:text-gray-400">
                                                Get reminded about scheduled workouts
                                            </p>
                                        </div>
                                        <Switch v-model:checked="userSettings.notifications.workoutReminders" />
                                    </div>

                                    <div class="flex items-center justify-between">
                                        <div class="flex-1">
                                            <Label>Weekly Reports</Label>
                                            <p class="text-sm text-gray-500 dark:text-gray-400">
                                                Receive weekly workout summary reports
                                            </p>
                                        </div>
                                        <Switch v-model:checked="userSettings.notifications.weeklyReport" />
                                    </div>

                                    <div class="flex items-center justify-between">
                                        <div class="flex-1">
                                            <Label>Achievement Alerts</Label>
                                            <p class="text-sm text-gray-500 dark:text-gray-400">
                                                Get notified when you unlock achievements
                                            </p>
                                        </div>
                                        <Switch v-model:checked="userSettings.notifications.achievementAlerts" />
                                    </div>
                                </div>

                                <div class="flex justify-end pt-4">
                                    <Button @click="saveNotificationSettings">
                                        Save Notification Settings
                                    </Button>
                                </div>
                            </CardContent>
                        </Card>
                    </TabsContent>

                    <!-- Privacy Tab -->
                    <TabsContent value="privacy" class="space-y-6">
                        <div>
                            <h2 class="text-2xl font-bold mb-2">Privacy & Security</h2>
                            <p class="text-gray-500 dark:text-gray-400">
                                Control who can see your profile and workout data.
                            </p>
                        </div>

                        <Card>
                            <CardContent class="p-6 space-y-6">
                                <div class="space-y-2">
                                    <Label>Profile Visibility</Label>
                                    <p class="text-sm text-muted-foreground">Control who can view your profile</p>
                                    <Select v-model="userSettings.privacy.profileVisibility">
                                        <SelectTrigger class="w-full max-w-xs">
                                            <Eye class="h-4 w-4 mr-2" />
                                            <SelectValue placeholder="Select visibility" />
                                        </SelectTrigger>
                                        <SelectContent>
                                            <SelectItem
                                                v-for="option in privacyOptions"
                                                :key="option.value"
                                                :value="option.value"
                                            >
                                                {{ option.label }}
                                            </SelectItem>
                                        </SelectContent>
                                    </Select>
                                </div>

                                <Separator />

                                <div class="space-y-4">
                                    <h3 class="font-semibold">Data Sharing</h3>

                                    <div class="flex items-center justify-between">
                                        <div class="flex-1">
                                            <Label>Show Workouts</Label>
                                            <p class="text-sm text-gray-500 dark:text-gray-400">
                                                Allow others to see your workout history
                                            </p>
                                        </div>
                                        <Switch v-model:checked="userSettings.privacy.showWorkouts" />
                                    </div>

                                    <div class="flex items-center justify-between">
                                        <div class="flex-1">
                                            <Label>Show Statistics</Label>
                                            <p class="text-sm text-gray-500 dark:text-gray-400">
                                                Allow others to see your workout statistics
                                            </p>
                                        </div>
                                        <Switch v-model:checked="userSettings.privacy.showStats" />
                                    </div>
                                </div>

                                <Separator />

                                <div class="space-y-4">
                                    <h3 class="font-semibold text-red-600 dark:text-red-400">
                                        Danger Zone
                                    </h3>

                                    <div class="p-4 bg-red-50 dark:bg-red-900/20 rounded-lg border border-red-200 dark:border-red-800">
                                        <h4 class="font-medium mb-2">Delete Account</h4>
                                        <p class="text-sm text-gray-600 dark:text-gray-400 mb-4">
                                            Permanently delete your account and all associated data. This action cannot be undone.
                                        </p>
                                        <Button variant="outline" class="text-red-600 border-red-300 hover:bg-red-50">
                                            Delete Account
                                        </Button>
                                    </div>
                                </div>

                                <div class="flex justify-end pt-4">
                                    <Button @click="savePrivacySettings">
                                        Save Privacy Settings
                                    </Button>
                                </div>
                            </CardContent>
                        </Card>
                    </TabsContent>
                </Tabs>
            </div>
        </div>
    </div>
</template>
