
<template>
    <UDashboardPanel id="settings">
        <template #header>
            <UDashboardNavbar title="Settings" :ui="{ right: 'gap-3' }">
                <template #leading>
                    <UDashboardSidebarCollapse />
                </template>
            </UDashboardNavbar>
        </template>

        <div class="p-4">
            <UDashboardSidebarCollapse />
            <h1 class="text-2xl font-bold mb-4">Settings Page</h1>
            <p>This is the settings page content.</p>
        </div>

        <div class="p-4">
            <h1 class="text-2xl font-bold mb-4">Settings Page</h1>
            <p>This is the settings page content.</p>
        </div>
    </UDashboardPanel>
    </template>