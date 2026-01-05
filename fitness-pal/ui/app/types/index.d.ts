import type { AvatarProps } from '@nuxt/ui-pro'

export type UserStatus = 'subscribed' | 'unsubscribed' | 'bounced'
export type SaleStatus = 'paid' | 'failed' | 'refunded'

export interface User {
  id: number
  name: string
  email: string
  avatar?: AvatarProps
  status: UserStatus
  location: string
}

export interface Mail {
  id: number
  unread?: boolean
  from: User
  subject: string
  body: string
  date: string
}

export interface Member {
  name: string
  username: string
  role: 'member' | 'owner'
  avatar: Avatar
}

export interface Stat {
  title: string
  icon: string
  value: number | string
  variation: number
  formatter?: (value: number) => string
}

export interface Sale {
  id: string
  date: string
  status: SaleStatus
  email: string
  amount: number
}

export interface Notification {
  id: number
  unread?: boolean
  sender: User
  body: string
  date: string
}

export type Period = 'daily' | 'weekly' | 'monthly'

export interface Range {
  start: Date
  end: Date
}

export type ActivityLevel = 'sedentary' | 'lightly_active' | 'moderately_active' | 'very_active' | 'extremely_active'
export type FitnessGoal = 'get_fit' | 'build_strength' | 'improve_endurance' | 'stay_healthy'

export interface UserProfile {
  id: string
  height: number
  weight: number
  gender: boolean
  activity_level: ActivityLevel
  goal: FitnessGoal
  created_at?: string
  updated_at?: string
}

export interface ProfileSetupFormData {
  height: number
  weight: number
  gender: boolean
  activity_level: ActivityLevel
  goal: FitnessGoal
}