export default defineEventHandler(async (event) => {
  const gridCode = getRouterParam(event, 'gridCode')
  const body = await readBody<Record<string, unknown>>(event)
  const backendUrl = process.env.BACKEND_URL || 'http://localhost:8080'
  const url = `${backendUrl}/api/v1/dynamic-grid/${gridCode}/bulk-delete`

  try {
    return await $fetch(url, {
      method: 'POST',
      body
    })
  } catch (error: unknown) {
    const err = error as any
    throw createError({
      statusCode: err.response?.status || 500,
      statusMessage: err.message || 'Internal Server Error'
    })
  }
})
