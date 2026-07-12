export default defineEventHandler(async (event) => {
  const gridCode = getRouterParam(event, 'gridCode')
  const body = await readBody(event)
  const backendUrl = process.env.BACKEND_URL || 'http://localhost:8080'
  const url = `${backendUrl}/api/v1/dynamic-grid/${gridCode}/bulk-delete`

  try {
    return await $fetch(url, {
      method: 'POST',
      body: body as Record<string, unknown>
    })
  } catch (error: any) { // eslint-disable-line @typescript-eslint/no-explicit-any
    throw createError({
      statusCode: error.response?.status || 500,
      statusMessage: error.message || 'Internal Server Error'
    })
  }
})
