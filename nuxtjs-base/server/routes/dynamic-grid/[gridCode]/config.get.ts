export default defineEventHandler(async (event) => {
  const gridCode = getRouterParam(event, 'gridCode')
  const backendUrl = process.env.BACKEND_URL || 'http://localhost:8080'
  const url = `${backendUrl}/api/v1/dynamic-grid/${gridCode}/config`

  try {
    return await $fetch(url, {
      method: 'GET'
    })
  } catch (error: unknown) {
    const err = error as any
    throw createError({
      statusCode: err.response?.status || 500,
      statusMessage: err.message || 'Internal Server Error'
    })
  }
})
